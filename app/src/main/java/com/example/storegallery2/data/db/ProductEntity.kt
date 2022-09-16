package com.example.storegallery2.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val title: String,
    val cover: String,
    val price: String,
    val liked: Boolean
)