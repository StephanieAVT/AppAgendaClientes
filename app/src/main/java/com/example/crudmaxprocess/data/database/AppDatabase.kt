package com.example.crudmaxprocess.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crudmaxprocess.data.database.dao.ClientDAO
import com.example.crudmaxprocess.data.database.entity.ClientEntity

@Database(entities = [ClientEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDAO

//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            synchronized(this) {
//                var instance: AppDatabase? = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context,
//                        AppDatabase::class.java,
//                    "client_database_app"
//                    ).build()
//                }
//                return instance
//            }
//        }
//    }
}