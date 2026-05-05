package com.sa.branchlocatormap.data.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.sa.branchlocatormap.data.dataSource.BankBranchLocalDataSource
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

class BankRepositoryTest {

    private lateinit var localDataSource: BankBranchLocalDataSource
    private lateinit var repository: BankRepository

    private val branches = listOf(
        BankBranchDetail(
            id = 1,
            name = "Sandton Branch",
            address = "Sandton",
            latitude = 1.0,
            longitude = 1.0
        )
    )

    @BeforeEach
    fun setup() {
        localDataSource = mockk()
        every { localDataSource.bankBranches } returns branches
        repository = BankRepository(localDataSource)
    }

    @Test
    fun `branches should return data`() {
        assertEquals(branches, repository.branches.value)
    }

    @Test
    fun `loadBranches should update state`() {
        val newBranches = listOf(
            BankBranchDetail(
                id = 2,
                name = "Rosebank Branch",
                address = "Rosebank",
                latitude = 2.0,
                longitude = 2.0
            )
        )

        every { localDataSource.bankBranches } returns newBranches

        repository.loadBranches()

        assertEquals(newBranches, repository.branches.value)
    }
}
