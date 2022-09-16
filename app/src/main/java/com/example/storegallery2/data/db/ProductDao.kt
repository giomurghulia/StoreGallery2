package com.example.storegallery2.data.db

import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todo: List<ProductEntity>)

    @Delete
    suspend fun delete(todo: ProductEntity)
}