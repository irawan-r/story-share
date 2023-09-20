package com.amora.storyappcompose.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amora.storyappcompose.data.MainRepository
import com.amora.storyappcompose.model.StoryItem
import com.amora.storyappcompose.model.Story
import com.amora.storyappcompose.ui.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<DataState<List<StoryItem>>?> = MutableStateFlow(null)
    val state: StateFlow<DataState<List<StoryItem>>?> = _state.asStateFlow()

    init {
        getStories()
    }

    fun getStories() {
        viewModelScope.launch {
            repository.getStories(page = null, size = null, location = null,
                onSuccess = {
                    _state.update { DataState.Success(it?.data) }
                }, onError = { error ->
                    _state.update { DataState.Error(error) }
                })
                .onStart {
                    _state.update { DataState.Loading() }
                }.collect()
        }
    }
}