package com.amora.storyappcompose.model

import com.squareup.moshi.Json

data class StoriesResponse(

	@Json(name="listStory")
	val listStory: List<StoryItem?>? = null,

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

data class StoryItem(

	@Json(name="photoUrl")
	val photoUrl: String? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="name")
	val name: String,

	@Json(name="description")
	val description: String? = null,

	@Json(name="lon")
	val lon: Double,

	@Json(name="id")
	val id: String,

	@Json(name="lat")
	val lat: Double
)
