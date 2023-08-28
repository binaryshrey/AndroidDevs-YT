package dev.shreyansh.androiddevsyt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import dev.shreyansh.androiddevsyt.database.AndroidDevDatabase
import dev.shreyansh.androiddevsyt.database.asDomainModel
import dev.shreyansh.androiddevsyt.domain.Video
import dev.shreyansh.androiddevsyt.network.AndroidDevAPI
import dev.shreyansh.androiddevsyt.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception


class AndroidDevsRepository(private val database: AndroidDevDatabase) {

    val videos: LiveData<List<Video>> = Transformations.map(database.databaseDao.getVideos()) {
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            try {
                val playlist = AndroidDevAPI.retrofitService.getVideos()
                database.databaseDao.insertAll(*playlist.asDatabaseModel())
            } catch (e: Exception) {
                Timber.i("${e.message}")
            }

        }
    }
}