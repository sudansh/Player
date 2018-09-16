package com.sudansh.player.di

import com.sudansh.player.data.LiveDataCallAdapterFactory
import com.sudansh.player.repository.remote.api.ApiService
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = applicationContext {
	bean { createWebService<ApiService>() }
}

inline fun <reified T> createWebService(): T = Retrofit.Builder()
	.baseUrl("https://api-v2.hearthis.at/")
	.addConverterFactory(GsonConverterFactory.create())
	.addCallAdapterFactory(LiveDataCallAdapterFactory())
	.build().create(T::class.java)