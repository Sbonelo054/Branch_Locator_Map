package com.sa.branchlocatormap.data.repository

import com.sa.branchlocatormap.data.dataSource.BranchLocatorDao
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FavouritesRepositoryTest {

    private lateinit var dao: BranchLocatorDao
    private lateinit var repository: FavouritesRepository

    @BeforeEach
    fun setup() {
        dao = mockk()
        repository = FavouritesRepository(dao)
    }

    @Test
    fun `saveFavouriteBranch should call dao`() = runTest {

        val branch = BankBranchDetail(
            id = 1,
            name = "Sandton Branch",
            address = "Sandton",
            latitude = 1.0,
            longitude = 1.0
        )

        coEvery { dao.saveFavourite(branch) } just Runs

        repository.saveFavouriteBranch(branch)

        coVerify(exactly = 1) { dao.saveFavourite(branch) }
    }

    @Test
    fun `getFavouriteBranches should return flow from dao`() = runTest {

        val branches = listOf(
            BankBranchDetail(
                id = 1,
                name = "Sandton Branch",
                address = "Sandton",
                latitude = 1.0,
                longitude = 1.0
            )
        )

        val flow = flowOf(branches)

        every { dao.getFavourites() } returns flow

        val result = repository.getFavouriteBranches()

        assertEquals(flow, result)

        verify(exactly = 1) { dao.getFavourites() }
    }

    @Test
    fun `deleteFavourite should call dao`() = runTest {

        val branch = BankBranchDetail(
            id = 1,
            name = "Sandton Branch",
            address = "Sandton",
            latitude = 0.0,
            longitude = 0.0
        )

        coEvery { dao.removeFavourite(branch) } just Runs

        repository.deleteFavourite(branch)

        coVerify(exactly = 1) { dao.removeFavourite(branch) }
    }
}