package com.amora.storyappcompose.ui

sealed class DataState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T? = null) : DataState<T>(data)
    class Loading<T>(data: T? = null) : DataState<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : DataState<T>(data, message)
}