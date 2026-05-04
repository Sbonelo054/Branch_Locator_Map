package com.sa.branchlocatormap

import com.sa.branchlocatormap.data.dataSource.BankBranchLocalDataSource
import com.sa.branchlocatormap.data.repository.BankRepository
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class BankRepositoryTest : FunSpec({

    lateinit var localDataSource: BankBranchLocalDataSource
    lateinit var repository: BankRepository

    val branches = listOf(
        BankBranchDetail(
            id = 1,
            name = "Sandton Branch",
            address = "Sandton Branch",
            latitude = 1.0,
            longitude = 1.0
        )
    )

    beforeTest {
        localDataSource = mockk()
        every { localDataSource.bankBranches } returns branches
        repository = BankRepository(localDataSource)
    }

    test("branches") {
        repository.branches.value shouldBe branches
    }

    test("loadBranches") {
        val newBranches = listOf(
            BankBranchDetail(
                id = 2,
                name = "Sandton Branch",
                address = "Sandton Branch",
                latitude = 2.0,
                longitude = 2.0
            )
        )

        every { localDataSource.bankBranches } returns newBranches

        repository.loadBranches()

        repository.branches.value shouldBe newBranches
    }
})