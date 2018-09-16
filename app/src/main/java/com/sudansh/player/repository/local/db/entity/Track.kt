package com.sudansh.player.repository.local.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sudansh.player.util.toTime
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Track(
	@PrimaryKey @SerializedName("id") val id: String,
	@SerializedName("title") val title: String,
	@SerializedName("created_at") val createdAt: String,
	@SerializedName("user_id") val userId: String,
	@SerializedName("duration") val duration: String,
	@SerializedName("permalink") val permalink: String,
	@SerializedName("description") val description: String,
	@SerializedName("genre") val genre: String,
	@SerializedName("uri") val uri: String,
	@SerializedName("artwork_url") val artworkUrl: String,
	@SerializedName("background_url") val backgroundUrl: String,
	@SerializedName("stream_url") val streamUrl: String,
	@SerializedName("thumb") val thumb: String
) : Parcelable {
	@Ignore @SerializedName("user") val artist: Artist? = null
	fun getTime() = duration.toTime()
}

@Entity
@Parcelize
data class Artist(
	@PrimaryKey @SerializedName("id") val id: String,
	@SerializedName("permalink") val permalink: String,
	@SerializedName("username") val username: String,
	@SerializedName("uri") val uri: String,
	@SerializedName("permalink_url") val permalinkUrl: String,
	@SerializedName("avatar_url") val avatarUrl: String
) : Parcelable