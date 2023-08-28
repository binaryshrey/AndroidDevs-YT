package dev.shreyansh.androiddevsyt.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.shreyansh.androiddevsyt.domain.Video

@Entity(tableName = "android_developers_video_table")
data class DatabaseEntity(
    @PrimaryKey
    val url : String,
    val title : String,
    val description : String,
    val updated : String,
    val thumbnail : String
)

//extension function which converts from database objects to domain objects
fun List<DatabaseEntity>.asDomainModel() : List<Video>{
    return map {
        Video(
            url = it.url,
            title = it.title,
            thumbnail = it.thumbnail,
            description = it.description,
            updated = it.updated
        )
    }
}