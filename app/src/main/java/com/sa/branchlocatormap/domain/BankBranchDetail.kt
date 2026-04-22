package com.sa.branchlocatormap.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_branch_detail")
data class BankBranchDetail(
    val name: String = "",
    val address: String = "",
    val distance: String = "",
    val isOpen: Boolean = true,
    val openTime: String = "",
    val closeTime: String = "",
    val phone: String = "",
    val services: List<String> = listOf(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
