package com.example.myapplication.db

import android.content.Context
import androidx.room.*
import com.example.myapplication.db.dao.TripDao
import com.example.myapplication.db.dao.UserDao
import com.example.myapplication.db.entities.TripEntity
import com.example.myapplication.db.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


@Database(version = 1, exportSchema = false, entities = arrayOf(
    UserEntity::class,
    TripEntity::class
))
@TypeConverters(Converter::class)

abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun tripDao() : TripDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }


        private fun buildDatabase(context: Context): AppDatabase {

            val db = Room.databaseBuilder(context, AppDatabase::class.java, "db_updriven.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

            return db
        }

    }
}