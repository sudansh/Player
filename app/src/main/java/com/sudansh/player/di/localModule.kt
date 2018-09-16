package com.sudansh.player.di

import android.arch.persistence.room.Room
import com.sudansh.player.data.AppExecutors
import com.sudansh.player.repository.MusicRepository
import com.sudansh.player.repository.local.db.AppDatabase
import org.koin.dsl.module.applicationContext

val localModule = applicationContext {
	bean { AppExecutors() }
	bean { MusicRepository(get(), get(), get(), get()) }
	bean {
		Room.databaseBuilder(get(), AppDatabase::class.java, "music-db").build()
	}
	bean { get<AppDatabase>().trackDao() }
	bean { get<AppDatabase>().userDao() }
}