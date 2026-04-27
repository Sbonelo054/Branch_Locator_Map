package com.sa.branchlocatormap.data.dataSource

import com.sa.branchlocatormap.domain.model.BankBranchDetail
/**
 * Local data source that provides a static list of bank branches.
 *
 * This class simulates a data provider (e.g., API or database) by returning
 * hardcoded branch information. It is useful for development, testing,
 * or offline support before integrating a real backend.
 */
class BankBranchLocalDataSource {

    /**
     * A list of available bank branches.
     *
     * Each [BankBranchDetail] contains:
     * - Basic branch info (name, address, phone)
     * - Operational info (open/close times, isOpen)
     * - User-related state (isFavourite)
     * - Services offered (ATM, loans, etc.)
     * - Location coordinates (latitude, longitude)
     * - Approximate distance (preformatted string, not dynamically calculated)
     *
     * NOTE:
     * - Distances are hardcoded and not calculated based on user location.
     * - `isOpen` is statically set and does not reflect real-time status.
     * - This should ideally be replaced with dynamic data from an API or database.
     */
    val bankBranches = listOf(
        BankBranchDetail(
            name = "Canal Walk, Cape Town",
            distance = "~1,400 km",
            address = "Shop 171, Canal Walk Shopping Centre, Century City, Century Blvd, Cape Town",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Transfers", "Deposits", "Withdrawals"),
            latitude = -33.8920,
            longitude = 18.5113
        ),
        BankBranchDetail(
            name = "Cape Gate, Brackenfell",
            distance = "~1,400 km",
            address = "Shop U65-U66, Cape Gate Shopping Centre, Okavango Rd, Brackenfell, Cape Town",
            isOpen = false,
            isFavourite = false,
            openTime = "08:00",
            closeTime = "17:00",
            phone = "0860 10 20 43",
            services = listOf(),
            latitude = -33.8535,
            longitude = 18.6978
        ),
        BankBranchDetail(
            name = "Adderley Street, Cape Town CBD",
            distance = "~1,400 km",
            address = "116 Adderley Street, Constitution House, Cape Town CBD",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Forex"),
            latitude = -33.9244,
            longitude = 18.4231
        ),
        BankBranchDetail(
            name = "Golden Acre, Cape Town CBD",
            distance = "~1,400 km",
            address = "Shop S41, Golden Acre Shopping Centre, Adderley St, Cape Town",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Deposits", "Withdrawals"),
            latitude = -33.9228,
            longitude = 18.4246
        ),
        BankBranchDetail(
            name = "Grand Central Branch, Cape Town CBD",
            distance = "~1,400 km",
            address = "Shop 20–21, Grand Central, 1 Parliament Street, Cape Town",
            isOpen = false,
            openTime = "08:00",
            isFavourite = false,
            closeTime = "17:00",
            phone = "0860 10 20 43",
            services = listOf("ATM", "Loans", "Transfers"),
            latitude = -33.9240,
            longitude = 18.4205
        ),
        BankBranchDetail(
            name = "Gardens Centre Branch, Cape Town",
            distance = "~1,400 km",
            address = "Shop 39A, Gardens Shopping Centre, Cnr Buitenkant & Mill St, Cape Town",
            isOpen = false,
            openTime = "08:00",
            isFavourite = false,
            closeTime = "17:00",
            phone = "0860 10 20 43",
            services = listOf("ATM", " Banking", "Deposits", "Withdrawals"),
            latitude = -33.9296,
            longitude = 18.4159
        ),
        BankBranchDetail(
            name = "Woodstock Branch, Cape Town",
            distance = "~1,400 km",
            address = "172 Victoria Rd, Woodstock, Cape Town",
            isOpen = false,
            openTime = "08:00",
            isFavourite = false,
            closeTime = "17:00",
            phone = "021 948 4508",
            services = listOf("ATM", "Banking", "Loans"),
            latitude = -33.9261,
            longitude = 18.4553
        ),
        BankBranchDetail(
            name = "Bellville, Cape Town",
            distance = "~1,400 km",
            address = "161 Voortrekker Rd, Bellville, Cape Town",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "021 948 4508",
            services = listOf("ATM", "Savings", "Loans", "Deposits"),
            latitude = -33.8942,
            longitude = 18.6297
        ),
        BankBranchDetail(
            name = "Philippi Branch, Cape Town",
            distance = "~1,400 km",
            address = "Corner Eisleben & Lansdowne Rd, Philippi, Cape Town",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "021 371 1262",
            services = listOf("ATM", " Banking", "Deposits", "Withdrawals"),
            latitude = -34.0318,
            longitude = 18.5645
        ),
        BankBranchDetail(
            name = "Retreat Branch, Cape Town",
            distance = "~1,400 km",
            address = "308 Main Rd, Retreat, Cape Town",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Loans"),
            latitude = -34.0695,
            longitude = 18.4672
        ),


        BankBranchDetail(
            name = "Sandton City Branch, Sandton",
            distance = "~0.5 km",
            address = "Sandton City Shopping Centre, 83 Rivonia Rd, Sandton, Johannesburg",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Card Services", "Deposits", "Withdrawals"),
            latitude = -26.1076,
            longitude = 28.0567
        ),
        BankBranchDetail(
            name = "Mandela Square, Sandton",
            distance = "~0.7 km",
            address = "Nelson Mandela Square, 5th St & Maude St, Sandton, Johannesburg",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Account Services", "Deposits", "Withdrawals"),
            latitude = -26.1079,
            longitude = 28.0547
        ),
        BankBranchDetail(
            name = "Morningside Branch, Sandton",
            distance = "~3.5 km",
            address = "Morningside Shopping Centre, Rivonia Rd, Morningside, Sandton",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Loans", "Deposits"),
            latitude = -26.0950,
            longitude = 28.0625
        ),
        BankBranchDetail(
            name = "Bryanston Branch, Sandton",
            distance = "~6 km",
            address = "Bryanston Shopping Centre, Main Rd, Bryanston, Johannesburg",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            phone = "0860 10 20 43",
            isFavourite = false,
            services = listOf("ATM", "Business Banking", "Deposits", "Withdrawals"),
            latitude = -26.0520,
            longitude = 28.0165
        ),
        BankBranchDetail(
            name = "Fourways Mall Branch, Sandton",
            distance = "~12 km",
            address = "Fourways Mall, William Nicol Dr, Fourways, Johannesburg",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Loans", "Forex"),
            latitude = -26.0167,
            longitude = 27.9972
        ),
        BankBranchDetail(
            name = "Rivonia Village Branch, Sandton",
            distance = "~5 km",
            address = "Rivonia Village Shopping Centre, Rivonia Rd, Rivonia, Sandton",
            isOpen = true,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Deposits", "Withdrawals", "Forex"),
            latitude = -26.0506,
            longitude = 28.0592
        ),
        BankBranchDetail(
            name = "Hyde Park, Sandton",
            distance = "~5.5 km",
            address = "Hyde Park Corner Shopping Centre, Jan Smuts Ave, Hyde Park, Johannesburg",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Forex", "Loans", "Account Services"),
            latitude = -26.1276,
            longitude = 28.0363
        ),
        BankBranchDetail(
            name = "Woodmead Retail Park Branch",
            distance = "~8 km",
            address = "Woodmead Retail Park, Woodmead Dr, Woodmead, Sandton",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Banking", "Deposits", "Withdrawals"),
            latitude = -26.0532,
            longitude = 28.1075
        ),
        BankBranchDetail(
            name = "Northgate Branch, Randburg (Near Sandton)",
            distance = "~14 km",
            address = "Northgate Shopping Centre",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Loans", "Deposits"),
            latitude = -26.0540,
            longitude = 27.9447
        ),
        BankBranchDetail(
            name = "Sandown Branch, Sandton",
            distance = "~2 km",
            address = "Sandown Retail Crossing",
            isOpen = false,
            openTime = "08:00",
            closeTime = "17:00",
            isFavourite = false,
            phone = "0860 10 20 43",
            services = listOf("ATM", "Deposits", "Withdrawals"),
            latitude = -26.1042,
            longitude = 28.0651
        )
    )
}