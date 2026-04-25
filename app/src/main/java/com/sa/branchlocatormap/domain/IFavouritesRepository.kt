package com.sa.branchlocatormap.domain

import kotlinx.coroutines.flow.Flow

interface IFavouritesRepository {

    suspend fun saveFavouriteBranch(bankBranchDetail: BankBranchDetail)

    suspend fun getFavouriteBranches(): Flow<List<BankBranchDetail>>

    suspend fun deleteFavourite(branch: BankBranchDetail)
}