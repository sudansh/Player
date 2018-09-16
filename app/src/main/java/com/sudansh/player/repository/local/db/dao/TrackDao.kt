package com.sudansh.player.repository.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.player.repository.local.db.entity.Track

@Dao
interface TrackDao {

	@Query("SELECT * FROM track")
	fun findAll(): LiveData<List<Track>>

	@Query("SELECT * FROM track WHERE id= :id")
	fun findById(id: String): LiveData<Track>

	@Query("SELECT * FROM track where userId like :userid")
	fun findByUserId(userid: String): LiveData<List<Track>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(track: Track)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(list: List<Track>)
}