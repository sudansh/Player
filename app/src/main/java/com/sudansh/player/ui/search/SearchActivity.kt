package com.sudansh.player.ui.search

import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import com.sudansh.player.R
import com.sudansh.player.data.Resource
import com.sudansh.player.data.Status
import com.sudansh.player.databinding.ActivitySearchBinding
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.ui.OnItemClickListener
import com.sudansh.player.ui.artist.ArtistAdapter
import com.sudansh.player.ui.track.TracksActivity
import com.sudansh.player.util.action
import com.sudansh.player.util.hideKeyboard
import com.sudansh.player.util.observeNonNull
import com.sudansh.player.util.snack
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity(), OnItemClickListener {

	private val artistAdapter by lazy { ArtistAdapter(this) }
	private val viewModel: SearchViewModel by inject()
	private lateinit var binding: ActivitySearchBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
		binding.viewModel = viewModel

		val divider =
			DividerItemDecoration(rvSearch.context, DividerItemDecoration.VERTICAL).apply {
				setDrawable(ContextCompat.getDrawable(baseContext, R.drawable.item_divider)!!)
			}
		with(rvSearch) {
			adapter = artistAdapter
			addItemDecoration(divider)
			itemAnimator = SlideInUpAnimator()
		}
		viewModel.results.observeNonNull(this) {
			updateUI(it)
		}
		search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				viewModel.setQuery(query)
				search_view.hideKeyboard()
				return true
			}

			override fun onQueryTextChange(newText: String): Boolean {
				return true
			}

		})
	}

	private fun updateUI(resource: Resource<List<Artist>>) {
		when (resource.status) {
			Status.SUCCESS -> {
				artistAdapter.updateItems(resource.data.orEmpty())
				viewModel.isLoading.set(false)
			}
			Status.LOADING -> viewModel.isLoading.set(true)
			Status.ERROR -> {
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

}
