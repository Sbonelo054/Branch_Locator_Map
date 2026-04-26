package com.sa.branchlocatormap.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Shared ViewModel used to hold and expose the currently selected bank branch.
 *
 * This ViewModel is intended for sharing state between different screens,
 * such as:
 * - Map screen → selecting a branch
 * - Detail screen → displaying selected branch info
 *
 * It acts as a simple in-memory state holder for the selected item.
 */
class BranchSharedViewModel : ViewModel() {

    /**
     * Internal mutable state holding the currently selected branch.
     *
     * Starts as null when no branch has been selected.
     */
    private val _selectedBranch = MutableStateFlow<BankBranchDetail?>(null)

    /**
     * Public read-only state for observing the selected branch.
     *
     * UI layers collect this to react to selection changes.
     */
    val selectedBranch = _selectedBranch.asStateFlow()

    /**
     * Updates the currently selected branch.
     *
     * @param branch The branch selected by the user.
     *
     * Behavior:
     * - Replaces the previous selection
     * - Notifies all collectors of the new value
     */
    fun selectBranch(branch: BankBranchDetail) {
        _selectedBranch.value = branch
    }
}