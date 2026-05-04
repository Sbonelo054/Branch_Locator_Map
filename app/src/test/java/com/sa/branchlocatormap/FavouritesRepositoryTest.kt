package com.sa.branchlocatormap

import com.sa.branchlocatormap.data.repository.FavouritesRepository

import com.sa.branchlocatormap.data.dataSource.BranchLocatorDao
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesRepositoryTest : FunSpec({

    lateinit var dao: BranchLocatorDao
    lateinit var repository: FavouritesRepository

    beforeTest {
        dao = mockk()
        repository = FavouritesRepository(dao)
    }

    test("saveFavouriteBranch") {
        runTest {
            val branch = BankBranchDetail(
                id = 1,
                name = "Sandton Branch",
                address = "Sandton Branch",
                latitude = 0.0,
                longitude = 0.0
            )

            coEvery { dao.saveFavourite(branch) } just Runs

            repository.saveFavouriteBranch(branch)

            coVerify(exactly = 1) { dao.saveFavourite(branch) }
            confirmVerified(dao)
        }
    }

    test("getFavouriteBranches") {
        runTest {
            val branches = listOf(
                BankBranchDetail(
                    id = 1,
                    name = "Sandton Branch",
                    address = "Sandton Branch",
                    latitude = 1.0,
                    longitude = 1.0
                )
            )

            val flow = flowOf(branches)

            coEvery { dao.getFavourites() } returns flow

            val result = repository.getFavouriteBranches()

            result shouldBe flow
            coVerify(exactly = 1) { dao.getFavourites() }
            confirmVerified(dao)
        }
    }

    test("deleteFavourite") {
        runTest {
            val branch = BankBranchDetail(
                id = 1,
                name = "Test Branch",
                address = "Test Address",
                latitude = 0.0,
                longitude = 0.0
            )

            coEvery { dao.removeFavourite(branch) } just Runs

            repository.deleteFavourite(branch)

            coVerify(exactly = 1) { dao.removeFavourite(branch) }
            confirmVerified(dao)
        }
    }
})