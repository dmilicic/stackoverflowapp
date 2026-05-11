package com.dmilicic.stackoverflowapp

import com.dmilicic.stackoverflowapp.api.ApiService
import com.dmilicic.stackoverflowapp.models.ApiResponse
import com.dmilicic.stackoverflowapp.models.UserModel
import com.dmilicic.stackoverflowapp.prefs.StackSharedPrefs
import com.dmilicic.stackoverflowapp.repositories.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before


class UserRepositoryTest {

    private var userRepository: UserRepository? = null

    private val apiService: ApiService = mockk(relaxed = true)
    private val sharedPrefs: StackSharedPrefs = mockk(relaxed = true)

    @Before
    fun setup() {
        userRepository = UserRepository(
            apiService = apiService,
            stackSharedPrefs = sharedPrefs
        )
    }

    @Test
    fun `get user details`() = runTest {
        coEvery { apiService.getTopUsers(any(), any() ) } returns ApiResponse(items = MOCK_USERS)

        val users = userRepository?.getTopUsers()

        assertNotNull(users)
        assertEquals(2, users?.size)
        assertEquals("John Doe", users?.get(0)?.displayName)
    }

    @Test
    fun `get user details - empty response`() = runTest {
        coEvery { apiService.getTopUsers(any(), any() ) } returns ApiResponse(items = emptyList())

        val users = userRepository?.getTopUsers()

        assertNotNull(users)
        assertEquals(0, users?.size)
    }

    @Test
    fun `get user details - exception`() = runTest {
        coEvery { apiService.getTopUsers(any(), any() ) } throws Exception("Network error")

        val users = userRepository?.getTopUsers()

        assertNotNull(users)
        assertEquals(0, users?.size)
    }

    @Test
    fun `following user`() = runTest {
        userRepository?.followUser(1)
        verify { sharedPrefs.saveFollowedUser(1, true) }
    }

    @Test
    fun `unfollow a user`() = runTest {
        userRepository?.unfollowUser(1)
        verify { sharedPrefs.saveFollowedUser(1, false) }
    }

    companion object {
        val MOCK_USERS = listOf(
            UserModel(
                userId = 1,
                accountId = 123,
                displayName = "John Doe",
                profileImage = "https://example.com/profile.jpg",
                reputation = 1000
            ),
            UserModel(
                userId = 2,
                accountId = 456,
                displayName = "Jane Smith",
                profileImage = "https://example.com/profile2.jpg",
                reputation = 2000
            )
        )
    }
}