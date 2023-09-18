package com.amora.storyappcompose.model


data class StoryRequest(
	val description: String,
	val photo: String = "",
	val lat: Double?,
	val lon: Double?
)