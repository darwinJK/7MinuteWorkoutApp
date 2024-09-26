package com.example.workoutapp

import android.app.Application

class ExerciseApp : Application(){
    val database by lazy {
        HistoryDatabase.getInstance(this)
    }
}