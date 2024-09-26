package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.databinding.ActivityExerciseFinishPageBinding

class ExerciseFinishPage : AppCompatActivity() {

    private var binding : ActivityExerciseFinishPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseFinishPageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinish)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinish?.setNavigationOnClickListener{
           onBackPressed()
        }

        binding?.finishBtn?.setOnClickListener {
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}