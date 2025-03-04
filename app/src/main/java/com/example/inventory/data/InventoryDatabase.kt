package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    // You only need one instance of the RoomDatabase for the whole app,
    // so make the RoomDatabase a singleton. Companion object la singleton
    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null
        // The Instance variable keeps a reference to the database,
        // when one has been created. This helps maintain a single instance of
        // the database opened at a given time, which is an expensive resource
        // to create and maintain.
        fun getDatabase(context: Context): InventoryDatabase {
            // tra ve Instance neu Instance khong null,
            // neu Instance la null thi chay block synchronized de tao moi 1 database
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}