package com.sudansh.player.ui.artist

import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.Toolbar
import android.util.Pair
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sudansh.player.R
import com.sudansh.player.data.Resource
import com.sudansh.player.data.Status
import com.sudansh.player.databinding.ActivityArtistBinding
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.ui.OnItemClickListener
import com.sudansh.player.ui.search.SearchActivity
import com.sudansh.player.ui.track.TracksActivity
import com.sudansh.player.util.action
import com.sudansh.player.util.observeNonNull
import com.sudansh.player.util.snack
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_artist.*
import org.koin.android.architecture.ext.viewModel

class ArtistsActivity : AppCompatActivity(), OnItemClickListener, Toolbar.OnMenuItemClickListener {

	private val artistAdapter by lazy { ArtistAdapter(this) }
	private val viewModel by viewModel<ArtistViewModel>()
	private lateinit var binding: ActivityArtistBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_artist)
		binding.viewModel = viewModel
		binding.setLifecycleOwner(this)

		toolbar.inflateMenu(R.menu.menu_main)
		toolbar.setOnMenuItemClickListener(this)
		val divider =
			DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL).apply {
				setDrawable(ContextCompat.getDrawable(this@ArtistsActivity,
													  R.drawable.item_divider)!!)
			}
		with(recyclerView) {
			adapter = artistAdapter
			addItemDecoration(divider)
			itemAnimator = SlideInUpAnimator()
		}
		swipe.setOnRefreshListener(viewModel::refresh)

		viewModel.tracks.observeNonNull(this) {
			updateUI(it)
		}
	}

	private fun updateUI(resource: Resource<List<Artist>>) {
		when (resource.status) {
			Status.SUCCESS -> {
				if (swipe.isRefreshing) swipe.isRefreshing = false
				artistAdapter.updateItems(resource.data.orEmpty())
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
		Intent(this, TracksActivity::class.java).apply {
			putExtra(TracksActivity.KEY_ARTIST, any as Artist)
		}.also { startActivity(it, options.toBundle()) }

	}

	override fun onMenuItemClick(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.menu_search -> {
				startActivity(Intent(this@ArtistsActivity, SearchActivity::class.java))
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}

}