package com.example.vapmzsem.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

class Converters{
    @TypeConverter
    fun LongToDate(lngDate : Long?) : Date?{
        return lngDate?.let { Date(it) }
    }
    @TypeConverter
    fun DateToLong(date : Date?) : Long?{
        return date?.time?.toLong()
    }
}
@Database(entities = [Fueling::class, Route::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routesDao() : RouteDao
    abstract fun fuelingDao() : FuelingDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, AppDatabase::class.java, "database").fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}

