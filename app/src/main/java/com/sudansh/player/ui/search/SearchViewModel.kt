package com.sudansh.player.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.sudansh.player.repository.MusicRepository
import com.sudansh.player.testing.OpenForTesting
import com.sudansh.player.util.switch

@OpenForTesting
class SearchViewModel(repo: MusicRepository) : ViewModel() {

	private val query = MutableLiveData<String>()
	val isLoading = ObservableBoolean(false)

	val results = query.switch { repo.search(it) }

	fun setQuery(originalInput: String) {
		val input = originalInput.toLowerCase().trim()
		if (input == query.value) {
			return
		}
		query.value = input
	}

	fun refresh() {
		query.value?.let {
			query.value = it
		}
	}

}
