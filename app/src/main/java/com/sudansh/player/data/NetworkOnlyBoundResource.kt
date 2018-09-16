package com.sudansh.player.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread

abstract class NetworkOnlyBoundResource<RequestType> {

	private val result = MediatorLiveData<Resource<RequestType>>()

	init {
		setValue(Resource.loading(null))
		fetchFromNetwork()
	}

	@MainThread
	private fun setValue(newValue: Resource<RequestType>) {
		if (result.value != newValue) {
			result.value = newValue
		}
	}

	private fun fetchFromNetwork() {
		val apiResponse = createCall()
		result.addSource(apiResponse) { response ->
			result.removeSource(apiResponse)

			when (response) {
				is ApiSuccessResponse -> {
					setValue(Resource.success(processResponse(response)))
				}

				is ApiErrorResponse -> {
					onFetchFailed()
					setValue(Resource.error(response.errorMessage, null))

				}
			}
		}
	}

	private fun onFetchFailed() {}

	fun asLiveData() = result as LiveData<Resource<RequestType>>

	@WorkerThread
	protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

	@MainThread
	protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}