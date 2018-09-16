package com.sudansh.player.ui.artist

import android.support.v7.util.DiffUtil
import com.sudansh.player.repository.local.db.entity.Artist

class ArtistDiffUtil(private val newList: List<Artist>, private val oldList: List<Artist>) :
		DiffUtil.Callback() {
	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
		newList[newItemPosition].id == oldList[oldItemPosition].id

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
		newList[newItemPosition] == oldList[oldItemPosition]

	override fun getOldListSize() = oldList.size

	override fun getNewListSize() = newList.size
}