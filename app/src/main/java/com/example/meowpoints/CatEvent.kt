package com.example.meowpoints

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_events")
data class CatEvent (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val points: Int,
    val timestamp: Long
)