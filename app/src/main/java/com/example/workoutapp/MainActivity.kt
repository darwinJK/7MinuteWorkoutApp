package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityMainBinding

private var binding : ActivityMainBinding? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.startBtnFl?.setOnClickListener {
            Toast.makeText(this,"button clicked",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,ExerciseActivity::class.java))
        }

        binding?.BmiFl?.setOnClickListener {
            startActivity(Intent(this,BMICalculatorActivity::class.java))
        }

        binding?.historyFl?.setOnClickListener {
            startActivity(Intent(this,HistoryActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}