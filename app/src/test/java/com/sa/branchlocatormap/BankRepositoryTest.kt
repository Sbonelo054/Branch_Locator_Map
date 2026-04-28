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
            name = "Branch A",
            address = "Address A",
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
        // Assert initial state after init block
        repository.branches.value shouldBe branches
    }

    test("loadBranches") {
        // Arrange
        val newBranches = listOf(
            BankBranchDetail(
                id = 2,
                name = "Branch B",
                address = "Address B",
                latitude = 2.0,
                longitude = 2.0
            )
        )

        every { localDataSource.bankBranches } returns newBranches

        // Act
        repository.loadBranches()

        // Assert
        repository.branches.value shouldBe newBranches
    }
})