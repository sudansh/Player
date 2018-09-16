package com.sudansh.player.ui.track

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.sudansh.player.data.Resource
import com.sudansh.player.repository.MusicRepository
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.repository.local.db.entity.Track
import com.sudansh.player.testing.OpenForTesting
import com.sudansh.player.util.switch

@OpenForTesting
class TrackViewModel(private val repo: MusicRepository) : ViewModel() {
	val artist: MutableLiveData<Artist> = MutableLiveData()
	val isLoading = ObservableBoolean(true)
	val tracks: LiveData<Resource<List<Track>>> =
		artist.switch { repo.getTrackByUsers(it.id) }

	fun setArtist(it: Artist) {
		artist.value = it
	}

	fun refresh() {
		artist.value?.let {
			artist.value = it
		}
	}
}