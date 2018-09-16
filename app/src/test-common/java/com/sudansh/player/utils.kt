package com.sudansh.player

import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.repository.local.db.entity.Track

fun createArtists(name: String): List<Artist> {
	return (1 until 10).map {
		Artist(it.toString(), "", name + it.toString(), "", "", "")
	}
}

fun createTrack(id: String, title: String): Track {
	return Track(id, title, "", "", "", "", "", "", "", "", "", "", "")
}

fun createTracks(title: String,
	count: Int = 10): List<Track> {
	return (1 until count).map {
		Track(it.toString(), title + it.toString(), "", "", "", "", "", "", "", "", "", "", "")
	}
}