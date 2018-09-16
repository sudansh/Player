package com.sudansh.player.ui

import android.widget.ImageView
import android.widget.TextView

interface OnItemClickListener {
	fun onItemClick(any: Any, image: ImageView, description: TextView)
}