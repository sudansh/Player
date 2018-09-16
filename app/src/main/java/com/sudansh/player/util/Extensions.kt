package com.sudansh.player.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
	this.observe(owner, Observer { it ->
		it?.let {
			observer(it)
		}
	})
}

fun <X, Y> LiveData<X>.switch(transform: (x: X) -> LiveData<Y>): LiveData<Y> {
	return Transformations.switchMap(this) {
		return@switchMap transform(it)
	}
}

inline fun View.snack(message: String,
	length: Int = Snackbar.LENGTH_LONG,
	action: Snackbar.() -> Unit = {}) {
	val snack = Snackbar.make(this, message, length)
	snack.action()
	snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
	setAction(action, listener)
	color?.let { setActionTextColor(color) }
}

fun String.toTime(): String {
	val duration = this.toInt()
	val minutes = duration % 3600 / 60
	val seconds = duration % 60
	return "$minutes:$seconds"
}

fun View.hideKeyboard(): Boolean {
	try {
		val inputMethodManager =
			context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
	} catch (ignored: RuntimeException) {
	}
	return false
}