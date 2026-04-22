package com.sa.branchlocatormap.domain

data class BankBranchDetail(
    val name: String = "",
    val address: String = "",
    val distance: String = "",
    val isOpen: Boolean = true,
    val openTime: String = "",
    val closeTime: String = "",
    val phone: String = "",
    val services: List<String> = listOf()
)
