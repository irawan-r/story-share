package com.amora.storyappcompose.model

import com.squareup.moshi.Json

data class StoryResponse(

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null,

	@Json(name="story")
	val story: StoryItem? = null
)
