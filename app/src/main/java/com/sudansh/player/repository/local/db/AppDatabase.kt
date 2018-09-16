package com.sudansh.player.repository.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sudansh.player.repository.local.db.dao.TrackDao
import com.sudansh.player.repository.local.db.dao.ArtistDao
import com.sudansh.player.repository.local.db.entity.Track
import com.sudansh.player.repository.local.db.entity.Artist

@Database(entities = [Track::class, Artist::class],
		  exportSchema = false,
		  version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract fun trackDao(): TrackDao
	abstract fun userDao(): ArtistDao
}