package com.example.workoutapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding? = null

    private var restTime : CountDownTimer?=null
    private var restProgress = 0

    private var exerciseTime: CountDownTimer? = null
    private var ExerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModels>? = null
    private var currentExercisePosition = -1

    private var textToSpeak : TextToSpeech? = null
    private var exerciseStatusAdapter : ExerciseStatusAdapter? = null

    private var restProgressTime = 30
    private var exerciseProgressTime =60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        textToSpeak = TextToSpeech(this,this)

        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseIst()

        //backButton
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressCustomDialog()
        }
        exerciseSetupView()
        setupExerciseStatus()

    }
    private fun setupExerciseStatus(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(
            this,LinearLayoutManager.HORIZONTAL,false)

        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseStatusAdapter
    }


    private fun exerciseSetupView(){
        if (restTime!=null) {
            restTime?.cancel()
            restProgress = 0
        }
        else if(exerciseTime!=null){
            exerciseTime?.cancel()
            ExerciseProgress=0
        }


        setRestProgressBar()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTime!=null) {
            restTime?.cancel()
            restProgress = 0
        }
        if(exerciseTime!=null){
            exerciseTime?.cancel()
            ExerciseProgress=0
        }
        if(textToSpeak!=null){
            textToSpeak?.stop()
            textToSpeak?.shutdown()
        }

        binding=null
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        onBackPressCustomDialog()
    }

    private fun setRestProgressBar(){
        restProgress=0
        restTransformation()
        if(restTime!=null){
            binding?.titleTv?.text = "Get ready for "
        }
        speakOut(text = "Next  is : ${binding?.exerciseNameTxt?.text.toString()} ready in  ")

        restTime = object : CountDownTimer((restProgressTime *1000).toLong(),1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress=restProgressTime-restProgress
                binding?.timerTxt?.text = (restProgressTime-restProgress).toString()
                if(restProgress>=0){
                    speakOut(binding?.timerTxt?.text.toString())
               }
            }

            override fun onFinish() {
                currentExercisePosition++
                speakOut("Lets start ${exerciseList?.get(currentExercisePosition)?.getName().toString()}")

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()

                exerciseStart()
                Toast.makeText(this@ExerciseActivity,"start exercise",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun exerciseStart() {
        ExerciseProgress=0
        exerciseTransformation()

        exerciseTime = object : CountDownTimer((exerciseProgressTime*1000).toLong(),1000){
            override fun onTick(millisUntilFinished: Long) {
                ExerciseProgress ++
                binding?.progressBar?.progress = exerciseProgressTime-ExerciseProgress
                binding?.timerTxt?.text = (exerciseProgressTime-ExerciseProgress).toString()
                if(ExerciseProgress>=0)
                    speakOut( binding?.timerTxt?.text.toString())
            }

            override fun onFinish() {
                if (currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseStatusAdapter!!.notifyDataSetChanged()
                    binding?.ivImage?.visibility = View.GONE
                    speakOut("release.")
                    setRestProgressBar()
                    Toast.makeText(this@ExerciseActivity,"Exercise complete!!.. Lets go to next Exercise",Toast.LENGTH_SHORT).show()
                }
                else{
                    speakOut("Congratulation!! you have completed 7 minute workout challenge.")
                    Toast.makeText(this@ExerciseActivity,"Congratulation!! you have completed.",Toast.LENGTH_SHORT).show()
                    moveToFinish()
                }
            }

        }.start()
    }

    private fun moveToFinish() {
        finish()
        val historyDao = (application as ExerciseApp).database.historyDao()
        Log.d("add to ","add to history is called")
        addToHistory(historyDao)
        startActivity(Intent(this,ExerciseFinishPage::class.java))
    }

    private fun addToHistory(historyDao: HistoryDao) {

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm",Locale.getDefault())
        val currentDate = sdf.format(Date())
        println("current Date : "+ currentDate)
        lifecycleScope.launch {
            historyDao.addDate(exerciseHistory(date = currentDate.toString()))
            Toast.makeText(this@ExerciseActivity,"added",Toast.LENGTH_SHORT).show()
            Log.d("added","data is added")
        }
    }

    private fun speakOut(text:String){
        textToSpeak?.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }


    private fun restTransformation() {

        binding?.exerciseNameTxt?.text = exerciseList!![currentExercisePosition+1].getName()
        binding?.upcomingTxt?.visibility = View.VISIBLE
        binding?.exerciseNameTxt?.visibility = View.VISIBLE
        binding?.progressBar?.progress = restProgress
    }

    private fun exerciseTransformation(){
        binding?.upcomingTxt?.visibility = View.GONE
        binding?.exerciseNameTxt?.visibility = View.GONE
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.titleTv?.text = exerciseList!![currentExercisePosition].getName()
        binding?.progressBar?.progress = restProgress
        binding?.ivImage?.visibility = View.VISIBLE
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result = textToSpeak!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this@ExerciseActivity,"not language supported",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this@ExerciseActivity,"not result supported",Toast.LENGTH_SHORT).show()

        }
    }

    private fun onBackPressCustomDialog(){
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.custom_dialogue)
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.findViewById<TextView>(R.id.positiveButton_tv).setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        customDialog.findViewById<TextView>(R.id.negativeButton_tv).setOnClickListener {

            customDialog.dismiss()
        }
        customDialog.show()

    }
}