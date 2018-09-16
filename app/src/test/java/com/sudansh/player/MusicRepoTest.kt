package com.sudansh.player

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.sudansh.player.repository.MusicRepository
import com.sudansh.player.repository.local.db.dao.ArtistDao
import com.sudansh.player.repository.local.db.dao.TrackDao
import com.sudansh.player.repository.remote.api.ApiService
import com.sudansh.player.util.InstantAppExecutors
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class MusicRepoTest {
	private val trackDao = mock(TrackDao::class.java)
	private val artistDao = mock(ArtistDao::class.java)
	private val api = mock(ApiService::class.java)
	private val repo = MusicRepository(InstantAppExecutors(), trackDao, artistDao, api)

	@Rule
	@JvmField
	val instantExecutorRule = InstantTaskExecutorRule()

	@Test
	fun `test load from db`() {
		repo.getTrending(false)
		verify(artistDao).findAll()
	}
}