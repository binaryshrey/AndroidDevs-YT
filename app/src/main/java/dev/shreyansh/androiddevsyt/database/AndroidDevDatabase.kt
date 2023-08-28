package dev.shreyansh.androiddevsyt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabaseEntity::class], exportSchema = false, version = 1)
abstract class AndroidDevDatabase : RoomDatabase() {
    abstract val databaseDao: DatabaseDao
}


//@Database(entities = [DatabaseEntity::class], exportSchema = true, version = 1)
//abstract class AndroidDevDatabase : RoomDatabase() {
//
//    abstract val databaseDao: DatabaseDao
//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: AndroidDevDatabase? = null
//
//        fun getInstance(context: Context): AndroidDevDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        AndroidDevDatabase::class.java,
//                        "android_developers_response_db"
//                    )
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}