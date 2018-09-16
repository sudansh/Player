package com.sudansh.player.repository

import android.arch.lifecycle.LiveData
import com.sudansh.player.data.ApiResponse
import com.sudansh.player.data.AppExecutors
import com.sudansh.player.data.NetworkBoundResource
import com.sudansh.player.data.NetworkOnlyBoundResource
import com.sudansh.player.data.Resource
import com.sudansh.player.repository.local.db.dao.ArtistDao
import com.sudansh.player.repository.local.db.dao.TrackDao
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.repository.local.db.entity.Track
import com.sudansh.player.repository.remote.api.ApiService
import com.sudansh.player.testing.OpenForTesting

@OpenForTesting
class MusicRepository(
	val appExecutors: AppExecutors,
	val trackDao: TrackDao,
	val artistDao: ArtistDao,
	val apiService: ApiService
) {

	fun getTrending(isFetch: Boolean = false): LiveData<Resource<List<Artist>>> {
		return object :
				NetworkBoundResource<List<Artist>, List<Track>>(appExecutors) {
			override fun saveCallResult(item: List<Track>) {
				item.filter { it.artist != null }
					.forEach { artistDao.insertIgnore(it.artist!!) }
				trackDao.insert(item)
			}

			override fun shouldFetch(data: List<Artist>?): Boolean =
				isFetch || data == null || data.isEmpty()

			override fun loadFromDb(): LiveData<List<Artist>> =
				artistDao.findAll()

			override fun createCall(): LiveData<ApiResponse<List<Track>>> =
				apiService.getTrending()
		}.asLiveData()
	}

	fun getTrackByUsers(userId: String): LiveData<Resource<List<Track>>> {
		return object :
				NetworkBoundResource<List<Track>, List<Track>>(appExecutors) {
			override fun saveCallResult(item: List<Track>) = trackDao.insert(item)

			override fun shouldFetch(data: List<Track>?) = true

			override fun loadFromDb(): LiveData<List<Track>> =
				trackDao.findByUserId(userId)

			override fun createCall(): LiveData<ApiResponse<List<Track>>> =
				apiService.getTrackByUser(userId)
		}.asLiveData()
	}

	fun search(query: String) =
		object : NetworkOnlyBoundResource<List<Artist>>() {
			override fun createCall() = apiService.search(query)
		}.asLiveData()
}