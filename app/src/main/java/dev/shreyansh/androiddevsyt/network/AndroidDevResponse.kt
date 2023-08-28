package dev.shreyansh.androiddevsyt.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import dev.shreyansh.androiddevsyt.database.DatabaseEntity
import dev.shreyansh.androiddevsyt.domain.Video
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class AndroidDevResponse(
    val videos: List<Video>
) : Parcelable


fun AndroidDevResponse.asDatabaseModel(): Array<DatabaseEntity> {
    return videos.map {
        DatabaseEntity (
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }.toTypedArray()
}