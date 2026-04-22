package com.sa.branchlocatormap.data

import com.sa.branchlocatormap.domain.BankBranchDetail

interface IFavouritesRepository {

    fun saveFavouriteBranch(bankBranchDetail: BankBranchDetail)

    fun getFavouriteBranches(): List<BankBranchDetail>

    fun deleteFavourite()
}