package com.sudansh.player.ui.track

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudansh.player.R
import com.sudansh.player.databinding.ItemTrackBinding
import com.sudansh.player.repository.local.db.entity.Track
import com.sudansh.player.ui.OnItemClickListener

class TrackAdapter(private var callback: OnItemClickListener) :
		RecyclerView.Adapter<TrackViewHolder>() {
	private val listTracks: MutableList<Track> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
		val binding = DataBindingUtil.inflate<ItemTrackBinding>(
			LayoutInflater.from(parent.context),
			R.layout.item_track,
			parent,
			false
		)
		return TrackViewHolder(binding)
	}

	override fun getItemCount() = listTracks.size

	override fun onBindViewHolder(vh: TrackViewHolder, position: Int) {
		val binding = vh.binding()
		binding.data = listTracks[position]
		binding.mainContainer.setOnClickListener {
			callback.onItemClick(listTracks[position], binding.image, binding.title)
		}
		binding.executePendingBindings()
	}

	fun updateItems(data: List<Track>) {
		val diffResult = DiffUtil.calculateDiff(TrackDiffUtil(data,
															  listTracks))
		listTracks.clear()
		listTracks.addAll(data)
		diffResult.dispatchUpdatesTo(this)
	}
}

class TrackViewHolder(private val binding: ItemTrackBinding) :
		RecyclerView.ViewHolder(binding.root) {

	fun binding() = binding
}
