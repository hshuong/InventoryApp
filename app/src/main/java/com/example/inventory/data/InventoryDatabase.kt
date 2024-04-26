package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Item::class, Category::class],
    version = 2,
    exportSchema = false)
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
                    .createFromAsset("item_database.db")
                    .fallbackToDestructiveMigration()
                    //.addMigrations(migration1)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

val migration1: Migration = object : Migration(1, 2) { override fun migrate(database: SupportSQLiteDatabase) {}}
