package com.amora.storyappcompose.model

import androidx.room.PrimaryKey
import com.amora.storyappcompose.utils.DataMapper.toStory
import javax.annotation.concurrent.Immutable


@Immutable
data class Story(
    @PrimaryKey
    val id: String,
    val photoUrl: String,
    val createdAt: String,
    val name: String,
    val description: String,
    val lon: Double,
    val lat: Double
) {
    companion object {
        fun mock() = Story(
            id = "0",
            photoUrl = "https://user-images.githubusercontent.com/24237865/75087936-5c1d9f80-553e-11ea-81d3-a912634dd8f7.jpg",
            createdAt = "12 Maret 2023",
            name = "Frozen II",
            description = "Frozen II, also known as Frozen 2, is a 2019 American 3D computer-animated musical fantasy film produced by Walt Disney Animation Studios. The 58th animated film produced by the studio, it is the sequel to the 2013 film Frozen and features the return of directors Chris Buck and Jennifer Lee, producer Peter Del Vecho, songwriters Kristen Anderson-Lopez and Robert Lopez, and composer Christophe Beck. Lee also returns as screenwriter, penning the screenplay from a story by her, Buck, Marc E. Smith, Anderson-Lopez, and Lopez,[2] while Byron Howard executive-produced the film.[a][1] Veteran voice cast Kristen Bell, Idina Menzel, Josh Gad, Jonathan Groff, and Ciarán Hinds return as their previous characters, and they are joined by newcomers Sterling K. Brown, Evan Rachel Wood, Alfred Molina, Martha Plimpton, Jason Ritter, Rachel Matthews, and Jeremy Sisto.",
            lat = 0.0,
            lon = 0.0
        ).toStory()
    }
}