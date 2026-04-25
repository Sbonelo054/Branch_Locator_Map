package com.sa.branchlocatormap.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.sa.branchlocatormap.domain.BankBranchDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BranchSharedViewModel: ViewModel() {
    private val _selectedBranch = MutableStateFlow<BankBranchDetail?>(null)
    val selectedBranch = _selectedBranch.asStateFlow()

    fun selectBranch(branch: BankBranchDetail) {
        _selectedBranch.value = branch
    }
}