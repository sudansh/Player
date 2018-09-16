package com.sudansh.player.ui.artist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.sudansh.player.data.Resource
import com.sudansh.player.repository.MusicRepository
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.testing.OpenForTesting
import com.sudansh.player.util.switch

@OpenForTesting
class ArtistViewModel(private val repo: MusicRepository) : ViewModel() {
	private val refresh: MutableLiveData<Boolean> = MutableLiveData()
	val isLoading = ObservableBoolean(true)
	val tracks: LiveData<Resource<List<Artist>>> =
		refresh.switch { startLoad ->
			repo.getTrending(startLoad)
		}

	init {
		refresh.value = false
	}

	private val query = MutableLiveData<String>()

	fun refresh() {
		refresh.value = true
	}
}