package com.amora.storyappcompose.model

import com.squareup.moshi.Json

data class NormalResponse(

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)
