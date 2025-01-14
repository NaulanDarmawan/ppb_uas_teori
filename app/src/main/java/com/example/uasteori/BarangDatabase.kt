package com.example.uasteori.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uasteori.dao.BarangDao
import com.example.uasteori.model.Barang

@Database(entities = [Barang::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun barangDao(): BarangDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "barang_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
