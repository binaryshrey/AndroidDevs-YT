package dev.shreyansh.androiddevsyt.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.shreyansh.androiddevsyt.database.AndroidDevDatabase
import dev.shreyansh.androiddevsyt.database.DatabaseDao
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AndroidDevDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            AndroidDevDatabase::class.java,
            "android_developers_response_db"
        )
            .build()
    }

    @Provides
    fun provideDao(androidDevDatabase: AndroidDevDatabase): DatabaseDao {
        return androidDevDatabase.databaseDao
    }

}