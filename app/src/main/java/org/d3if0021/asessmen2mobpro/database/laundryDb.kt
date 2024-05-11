package org.d3if3062.mobpro1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3062.mobpro1.model.Laundry


@Database(entities = [Laundry::class], version = 1, exportSchema = false)
abstract class laundryDb : RoomDatabase() {
    abstract val dao: laundryShopDao
    companion object{
        @Volatile
        private var INSTANCE: laundryDb? = null
        fun getInstance(context: Context): laundryDb {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        laundryDb::class.java,
                        "Laundry_db2"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}