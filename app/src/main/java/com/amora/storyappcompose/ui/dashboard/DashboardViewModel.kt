package com.amora.storyappcompose.ui.dashboard

import androidx.lifecycle.ViewModel
import com.amora.storyappcompose.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
}