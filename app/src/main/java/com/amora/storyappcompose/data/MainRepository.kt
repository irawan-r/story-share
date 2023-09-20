package com.amora.storyappcompose.data

import com.amora.storyappcompose.model.LoginRequests
import com.amora.storyappcompose.model.LoginResponse
import com.amora.storyappcompose.model.RegisterRequest
import com.amora.storyappcompose.model.StoriesResponse
import com.amora.storyappcompose.model.StoryRequest
import com.amora.storyappcompose.model.User
import com.amora.storyappcompose.network.ApiService
import com.amora.storyappcompose.utils.ApiUtils.toMultiPartBody
import com.amora.storyappcompose.utils.ApiUtils.toRequestBodyPart
import com.google.gson.Gson
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.io.File
import javax.inject.Inject

class MainRepository @Inject constructor(
	private val sessionManager: SessionManager,
	private val apiService: ApiService
) {
	private fun setSession(user: User) {
		sessionManager.loginSession(user)
	}

	fun deleteSession() {
		sessionManager.deleteSession()
	}

	private fun getSession(): Flow<User?> {
		return flow {
			emit(sessionManager.getSession())
		}.flowOn(Dispatchers.Default)
	}

	fun login(
		request: LoginRequests,
		onSuccess:(LoginResponse) -> Unit,
		onError: (String) -> Unit
	) = flow {
		val loginRequest = apiService.login(request.email, request.password)
		loginRequest.suspendOnSuccess {
			data.loginResult?.let { setSession(it) }
			emit(data)
			onSuccess(data)
		}.onError {
			val message: String? = try {
				val errorMessageObj = Gson().fromJson(message(), LoginResponse::class.java)
				errorMessageObj.message?.replace("\"", "")
			} catch (e: Exception) {
				onError(e.message.toString())
				null
			}
			if (message != null) {
				onError(message)
			}
		}.onException {
			onError(this.message.toString())
		}
	}.flowOn(Dispatchers.IO)

	fun register(
		request: RegisterRequest,
		onStart: () -> Unit,
		onError: (String) -> Unit,
		onCompletion: () -> Unit
	) = flow {
		val registerRequest = apiService.register(request)
		registerRequest.suspendOnSuccess {
			emit(data.message)
		}.onFailure {
			onError(message())
		}.onError {
			onError(message())
		}
	}.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

	fun postStory(
		request: StoryRequest,
		onStart: () -> Unit,
		onError: (String) -> Unit,
		onCompletion: () -> Unit
	) = flow {
		val userSession = getSession().firstOrNull()
		val token = "Bearer ${userSession?.token}"
		val description = request.description.toRequestBodyPart()
		val photoUri = request.photo
		val filePhoto = File(photoUri)
		val latitude = request.lat.toRequestBodyPart()
		val longitude = request.lon.toRequestBodyPart()
		val postRequest = apiService.postStories(
			token = token,
			desc = description,
			filePhoto = photoUri.toRequestBodyPart().toMultiPartBody(filePhoto, "photo"),
			latitude = latitude,
			longitude = longitude
		)
		postRequest.suspendOnSuccess {
			emit(data.message)
		}.onFailure {
			onError(message())
		}.onError {
			onError(message())
		}
	}.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

	fun getStories(
		page: Int?,
		size: Int?,
		location: Double?,
		onSuccess: (StoriesResponse) -> Unit,
		onError: (String) -> Unit
	) = flow {
		val userSession = getSession().firstOrNull()
		val token = "Bearer ${userSession?.token}"
		val getStories =
			apiService.getAllStories(token = token, page = page, size = size, location = location)
		getStories.suspendOnSuccess {
			emit(data)
			onSuccess(data)
		}.onFailure {
			onError(message())
		}.onError {
			onError(message())
		}
	}.flowOn(Dispatchers.IO)

	fun getStory(
		id: String,
		onStart: () -> Unit,
		onError: (String) -> Unit,
		onCompletion: () -> Unit
	) = flow {
		val userSession = getSession().firstOrNull()
		val token = "Bearer ${userSession?.token}"
		val getStory = apiService.getStoriesById(token = token, id = id)
		getStory.suspendOnSuccess {
			emit(data.message)
		}.onFailure {
			onError(message())
		}.onError {
			onError(message())
		}
	}.onStart {
		onStart()
	}.onCompletion {
		onCompletion()
	}.flowOn(Dispatchers.IO)
}