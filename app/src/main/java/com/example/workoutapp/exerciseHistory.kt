package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class exerciseHistory(
    @PrimaryKey
    val date : String =""
)
