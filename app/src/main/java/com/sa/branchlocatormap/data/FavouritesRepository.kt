package com.sa.branchlocatormap.data

import android.app.Application
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IFavouritesRepository
import kotlinx.coroutines.flow.Flow

class FavouritesRepository(
    private val dao: BranchLocatorDao
) : IFavouritesRepository {

    override suspend fun saveFavouriteBranch(bankBranchDetail: BankBranchDetail) {
        dao.saveFavourite(bankBranchDetail)
    }

    override suspend fun getFavouriteBranches(): Flow<List<BankBranchDetail>> {
        return dao.getFavourites()
    }

    override suspend fun deleteFavourite(branch: BankBranchDetail) {
        dao.removeFavourite(branch)
    }
}