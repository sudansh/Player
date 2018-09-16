package com.sudansh.player.ui.musicplayer

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sudansh.player.R
import com.sudansh.player.repository.local.db.entity.Track
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

	private lateinit var track: Track

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_player)
		postponeEnterTransition()
		track = intent.getParcelableExtra(KEY_TRACK)
		initializePlayer(track)
	}

	private var player: SimpleExoPlayer? = null

	private fun initializePlayer(track: Track) {
		track_title.text = track.title
		Glide.with(this).load(track.artworkUrl).into(artwork)
		startPostponedEnterTransition()
		// Create a default TrackSelector
		val bandwidthMeter = DefaultBandwidthMeter()
		val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
		val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

		//Initialize the player
		player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

		exoplayer.controllerHideOnTouch = false
		exoplayer.player = player

		// Produces DataSource instances through which media data is loaded.
		val dataSourceFactory =
			DefaultDataSourceFactory(this, Util.getUserAgent(this, "musicplayer"))

		val videoUri = Uri.parse(track.streamUrl)
		val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
			.setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(videoUri)

		// Prepare the player with the source.
		player?.prepare(videoSource)
		player?.playWhenReady = true

	}

	override fun onStop() {
		super.onStop()
		releasePlayer()
	}

	private fun releasePlayer() {
		if (player != null) {
			player?.release()
			player = null
		}
	}

	companion object {
		const val KEY_TRACK = "KEY_TRACK"
		const val Broadcast_PLAY_NEW_AUDIO = "PlayNewAudio"
	}
}
