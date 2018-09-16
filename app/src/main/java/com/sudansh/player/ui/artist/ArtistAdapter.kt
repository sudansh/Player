package com.sudansh.player.ui.artist

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudansh.player.R
import com.sudansh.player.databinding.ItemArtistBinding
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.ui.OnItemClickListener

class ArtistAdapter(private var callback: OnItemClickListener) :
		RecyclerView.Adapter<ArtistViewHolder>() {
	private val listUsers: MutableList<Artist> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
		val binding = DataBindingUtil.inflate<ItemArtistBinding>(
			LayoutInflater.from(parent.context),
			R.layout.item_artist,
			parent,
			false
		)
		return ArtistViewHolder(binding)
	}

	override fun getItemCount() = listUsers.size

	override fun onBindViewHolder(vh: ArtistViewHolder, position: Int) {
		val binding = vh.binding()
		binding.data = listUsers[position]
		binding.mainContainer.setOnClickListener {
			callback.onItemClick(listUsers[position], binding.image, binding.title)
		}
		binding.executePendingBindings()
	}

	fun updateItems(data: List<Artist>) {
		val diffResult = DiffUtil.calculateDiff(ArtistDiffUtil(data,
															   listUsers))
		listUsers.clear()
		listUsers.addAll(data)
		diffResult.dispatchUpdatesTo(this)
	}
}

class ArtistViewHolder(private val binding: ItemArtistBinding) :
		RecyclerView.ViewHolder(binding.root) {

	fun binding() = binding
}