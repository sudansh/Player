package com.sudansh.player.di

import com.sudansh.player.ui.track.TrackViewModel
import com.sudansh.player.ui.artist.ArtistViewModel
import com.sudansh.player.ui.search.SearchViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val appModule = applicationContext {

	viewModel { ArtistViewModel(get()) }
	viewModel { TrackViewModel(get()) }
	viewModel { SearchViewModel(get()) }
}