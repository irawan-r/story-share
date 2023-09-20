package com.amora.storyappcompose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amora.storyappcompose.data.MainRepository
import com.amora.storyappcompose.model.LoginRequests
import com.amora.storyappcompose.model.LoginResponse
import com.amora.storyappcompose.ui.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<DataState<LoginResponse?>?> =
        MutableStateFlow(null)
    val state: StateFlow<DataState<LoginResponse?>?> = _state.asStateFlow()

    init {
        Timber.d("Injection LoginViewModel")
    }

    fun login(request: LoginRequests) {
        viewModelScope.launch {
            loginProceed(request)
        }
    }

    private fun loginProceed(request: LoginRequests) {
        viewModelScope.launch {
            repository.login(
                request = request,
                onSuccess = {
                    _state.value = DataState.Success(it)
                },
                onError = {
                    updateError(it)
                }).onStart {
                _state.value = DataState.Loading()
            }.collect()
        }
    }

    fun updateError(message: String) {
        _state.value = DataState.Error(message)
    }

    fun resetState() {
        _state.update { null }
    }

}