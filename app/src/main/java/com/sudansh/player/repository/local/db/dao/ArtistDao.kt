package com.sudansh.player.repository.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.player.repository.local.db.entity.Artist

@Dao
interface ArtistDao {

	@Query("SELECT * FROM artist")
	fun findAll(): LiveData<List<Artist>>

	@Query("SELECT * FROM artist WHERE id= :id")
	fun findById(id: String): LiveData<Artist>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insertIgnore(track: Artist)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertReplace(track: Artist)

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insertIgnore(list: List<Artist>)
}