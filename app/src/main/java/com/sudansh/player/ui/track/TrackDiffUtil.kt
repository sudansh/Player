package com.sudansh.player.ui.track

import android.support.v7.util.DiffUtil
import com.sudansh.player.repository.local.db.entity.Track

class TrackDiffUtil(private val newList: List<Track>, private val oldList: List<Track>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            newList[newItemPosition].id == oldList[oldItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            newList[newItemPosition] == oldList[oldItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
}