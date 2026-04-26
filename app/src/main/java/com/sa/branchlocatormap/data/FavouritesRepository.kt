package com.sa.branchlocatormap.data

import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IFavouritesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for managing favourite bank branches.
 *
 * This acts as an abstraction layer over the [BranchLocatorDao],
 * allowing the rest of the app to interact with favourite data
 * without depending directly on Room.
 *
 * Responsibilities:
 * - Save a branch as favourite
 * - Retrieve favourite branches
 * - Remove a branch from favourites
 */
class FavouritesRepository(
    private val dao: BranchLocatorDao
) : IFavouritesRepository {

    /**
     * Saves a bank branch as a favourite.
     *
     * @param bankBranchDetail The branch to be stored.
     *
     * Behavior:
     * - Delegates directly to the DAO.
     * - If the branch already exists, it will be replaced
     *   (based on Room's REPLACE strategy).
     */
    override suspend fun saveFavouriteBranch(bankBranchDetail: BankBranchDetail) {
        dao.saveFavourite(bankBranchDetail)
    }

    /**
     * Retrieves all favourite branches.
     *
     * @return A [Flow] emitting a list of favourite branches.
     *
     * Behavior:
     * - Emits updates automatically whenever the database changes.
     * - Suitable for direct observation in ViewModels/UI.
     */
    override suspend fun getFavouriteBranches(): Flow<List<BankBranchDetail>> {
        return dao.getFavourites()
    }

    /**
     * Removes a branch from favourites.
     *
     * @param branch The branch to delete.
     *
     * Behavior:
     * - Delegates deletion to the DAO.
     */
    override suspend fun deleteFavourite(branch: BankBranchDetail) {
        dao.removeFavourite(branch)
    }
}