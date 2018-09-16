package com.sudansh.player.repository.remote.api

import android.arch.lifecycle.LiveData
import com.sudansh.player.data.ApiResponse
import com.sudansh.player.repository.local.db.entity.Artist
import com.sudansh.player.repository.local.db.entity.Track
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	@GET("feed?count=20&type=new")
	fun getTrending(): LiveData<ApiResponse<List<Track>>>

	@GET("{user}")
	fun getTrackByUser(
		@Path("user") user: String,
		@Query("count") count: Int = 20,
		@Query("type") type: String = "tracks"): LiveData<ApiResponse<List<Track>>>

	@GET("search")
	fun search(
		@Query("t") query: String,
		@Query("count") count: Int = 20,
		@Query("type") type: String = "user"): LiveData<ApiResponse<List<Artist>>>
}