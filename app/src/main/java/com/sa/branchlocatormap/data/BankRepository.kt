package com.sa.branchlocatormap.data

import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IBankRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BankRepository: IBankRepository {

    private val _branches = MutableStateFlow<List<BankBranchDetail>>(emptyList())

    override val branches: StateFlow<List<BankBranchDetail>> = _branches

    init {
        loadBranches()
    }

     override fun loadBranches() {
        _branches.value = listOf(
            BankBranchDetail(
                name = "Sandton City",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                latitude = -26.1076,
                longitude = 28.0567
            ),
            BankBranchDetail(
                "Nelson Mandela Square Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                latitude = -26.1079,
                longitude = 28.0569
            ),
            BankBranchDetail(
                "Rosebank Mall Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                latitude = -26.1456,
                longitude = 28.0436
            ),
            BankBranchDetail(
                "Morningside Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                latitude = -26.0937,
                longitude = 28.0583
            ),
            BankBranchDetail(
                "Bryanston Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                phone = "+27 11 123 4567",
                latitude = -26.0489,
                longitude = 28.0287
            ),
            BankBranchDetail(
                "Benmore Gardens Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                latitude = -26.1024,
                longitude = 28.0618
            ),
            BankBranchDetail(
                "Grayston Drive Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                latitude = -26.0975,
                longitude = 28.0542
            ),
            BankBranchDetail(
                "Illovo Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                latitude = -26.1302,
                longitude = 28.0516
            ),
            BankBranchDetail(
                "Woodmead Midrand",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                latitude = -26.0449,
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                longitude = 28.1021
            ),
            BankBranchDetail(
                "Rivonia Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                latitude = -26.0535,
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans"),
                longitude = 28.0590
            ),
            BankBranchDetail(
                name = "Standard Bank - Sandton",
                distance = "1.2 km",
                address = "123 Rivonia Rd, Sandton",
                isOpen = false,
                openTime = "08:00",
                closeTime = "16:30",
                phone = "+27 11 123 4567",
                latitude = -27.090,
                longitude = 28.009786,
                services = listOf("ATM", "Forex", "Business Banking", "Home Loans")
            ),
        )
    }
}