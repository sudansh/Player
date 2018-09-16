package com.sudansh.player.ui.track

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sudansh.player.R
import com.sudansh.player.data.Resource
import com.sudansh.player.data.Status
import com.sudansh.player.databinding.ActivityTrackBinding
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.repository.local.db.entity.Track
import com.sudansh.player.ui.OnItemClickListener
import com.sudansh.player.ui.musicplayer.PlayerActivity
import com.sudansh.player.util.action
import com.sudansh.player.util.observeNonNull
import com.sudansh.player.util.snack
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_track.*
import org.koin.android.architecture.ext.viewModel

class TracksActivity : AppCompatActivity(), OnItemClickListener {
	private val viewModel by viewModel<TrackViewModel>()
	private val trackAdapter by lazy { TrackAdapter(this) }
	private lateinit var binding: ActivityTrackBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_track)
		binding.viewModel = viewModel

		toolbar.setNavigationOnClickListener { onBackPressed() }
		if (intent.getParcelableExtra<Artist>(KEY_ARTIST) == null) finish()
		viewModel.setArtist(intent.getParcelableExtra(KEY_ARTIST))
		with(rvTracks) {
			adapter = trackAdapter
			itemAnimator = SlideInUpAnimator()
		}
		viewModel.tracks.observeNonNull(this) {
			updateUI(it)
		}
		swipe.setOnRefreshListener(viewModel::refresh)
	}

	private fun updateUI(resource: Resource<List<Track>>) {
		when (resource.status) {
			Status.SUCCESS -> {
				if (swipe.isRefreshing) swipe.isRefreshing = false
				trackAdapter.updateItems(resource.data.orEmpty())
				viewModel.isLoading.set(false)
			}
			Status.LOADING -> viewModel.isLoading.set(true)
			Status.ERROR -> {
				if (swipe.isRefreshing) swipe.isRefreshing = false
				viewModel.isLoading.set(false)
				showError()
			}
		}

	}

	private fun showError() {
		mainContainer.snack(getString(R.string.error_message), Snackbar.LENGTH_INDEFINITE) {
			action(getString(R.string.yes)) { viewModel.refresh() }
		}
	}

	override fun onItemClick(any: Any, image: ImageView, description: TextView) {
		val options = ActivityOptions.makeSceneTransitionAnimation(this,
																   Pair.create<View, String>(image,
																							 getString(
																								 R.string.transitionImage)),
																   Pair.create<View, String>(
																	   description,
																	   getString(R.string.transitionDescription)))
		Intent(this, PlayerActivity::class.java).apply {
			putExtra(PlayerActivity.KEY_TRACK, any as Track)
		}.also { startActivity(it, options.toBundle()) }

	}

	override fun onBackPressed() {
		supportFinishAfterTransition()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> {
				supportFinishAfterTransition()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}

	companion object {
		const val KEY_ARTIST = "KEY_ARTIST"

		fun start(context: Context, location: Artist) {
			Intent(context, TracksActivity::class.java).apply {
				putExtra(KEY_ARTIST, location)
			}.also { context.startActivity(it) }
		}
	}

}
