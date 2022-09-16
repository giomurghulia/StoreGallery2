package com.example.storegallery2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storegallery2.data.db.ProductDao
import com.example.storegallery2.data.db.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}