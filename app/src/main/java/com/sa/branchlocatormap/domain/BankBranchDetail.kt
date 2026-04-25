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
    val isFavourite: Boolean = false,
    val phone: String = "",
    val services: List<String> = listOf(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)