package com.sudansh.player.util

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.view.View
import android.widget.ImageView
import com.sudansh.player.GlideApp
import com.sudansh.player.R

@BindingAdapter("imageUrl")
fun setImage(view: ImageView, url: String?) {
    GlideApp.with(view)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .override(200)
            .into(view)
}

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}