package com.example.workoutapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    suspend fun addDate(exerciseHistory: exerciseHistory)

    @Query("SELECT * FROM 'history_table'")
    fun fetAllData(): Flow<List<exerciseHistory>>
}