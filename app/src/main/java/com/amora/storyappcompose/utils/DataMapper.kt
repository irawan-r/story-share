package com.amora.storyappcompose.utils

import com.amora.storyappcompose.model.Story
import com.amora.storyappcompose.model.StoryItem

object DataMapper {

    fun Story.toStory(): StoryItem {
        return StoryItem(
            id = id,
            photoUrl = photoUrl,
            createdAt = createdAt,
            name = name,
            description = description,
            lon = lon,
            lat = lat
        )
    }
}