package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmicalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMICalculatorActivity : AppCompatActivity() {

    private var binding : ActivityBmicalculatorBinding? = null
    private var radioButtonSelected = "MetricsUnit"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityBmicalculatorBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }
        binding?.calculationBmiBtn?.setOnClickListener {
            calculateBmi()
        }
        binding?.usUnitRb?.setOnClickListener {
            radioButtonSelected = "UsUnit"
           visibilityConditionForUSUnitRadio()
        }
        binding?.metricUnitRb?.setOnClickListener {
           radioButtonSelected = "MetricsUnit"
            visibilityConditionForMatrixUnitRadio()
        }
    }

    private fun visibilityConditionForMatrixUnitRadio() {
        binding?.inchesEt?.text = null
        binding?.feetEt?.text = null
        binding?.weightTxt?.text = null
        binding?.weightTil?.visibility = View.VISIBLE
        binding?.heightTil?.visibility = View.VISIBLE
        binding?.yourBmiTxt?.visibility=View.GONE
        binding?.BmiValueTxt?.visibility = View.GONE
        binding?.statusBmiTxt?.visibility = View.GONE
        binding?.descriptionBmiTxt?.visibility = View.GONE
        binding?.inchFeetLl?.visibility = View.GONE
    }

    private fun visibilityConditionForUSUnitRadio() {
        binding?.heightTxt?.text = null
        binding?.weightTxt?.text=null
        binding?.weightTil?.visibility = View.GONE
        binding?.heightTil?.visibility = View.GONE
        binding?.yourBmiTxt?.visibility=View.GONE
        binding?.BmiValueTxt?.visibility = View.GONE
        binding?.statusBmiTxt?.visibility = View.GONE
        binding?.descriptionBmiTxt?.visibility = View.GONE
        binding?.inchFeetLl?.visibility = View.VISIBLE

    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }

    override fun onBackPressed() {
        binding?.heightTxt?.text=null
        binding?.weightTxt?.text=null
        binding?.yourBmiTxt?.visibility=View.GONE
        binding?.BmiValueTxt?.visibility = View.GONE
        binding?.statusBmiTxt?.visibility = View.GONE
        binding?.descriptionBmiTxt?.visibility = View.GONE
        super.onBackPressed()

    }

    private fun calculateBmi() {
        val weightInPounds = binding?.weightEt?.text.toString()
        val weightInKg = binding?.weightTxt?.text.toString()
        val height = binding?.heightTxt?.text.toString()
        when{
            (weightInKg.isEmpty() && height.isEmpty()) && (weightInPounds.isEmpty()) ->
                        Toast.makeText(this@BMICalculatorActivity,"please enter valid details and fill required columns",Toast.LENGTH_SHORT).show()

            else->{
                Log.d("radioButtonSelected ","$radioButtonSelected")
                if(radioButtonSelected == "MetricsUnit"){
                    ResultOfBmi((height.toFloat())/100,weightInKg.toFloat())
                }
                else{
                    convertUsUnit()
                }
            }
        }
    }

    private fun convertUsUnit() {
        var height = ""
        val weightInPounds = binding?.weightEt?.text.toString()
        val feet = binding?.feetEt?.text.toString()
        val inch = binding?.inchesEt?.text.toString()
        val weight = (weightInPounds.toFloat() * 0.45).toFloat()
        when {
            feet.isEmpty() && inch.isEmpty()->
                Toast.makeText(
                    this@BMICalculatorActivity,
                    "please enter the height in either feet or inches",
                    Toast.LENGTH_SHORT
                ).show()

            feet.isNotEmpty() && inch.isNotEmpty() ->
                Toast.makeText(
                    this@BMICalculatorActivity,
                    "please enter the height in either feet or inches",
                    Toast.LENGTH_SHORT
                ).show()
            feet.isNotEmpty() ->{
                height = (feet.toFloat() * 30.48).toString()
                ResultOfBmi((height.toFloat())/100,weight)
            }
            inch.isNotEmpty() ->{
                height = (inch.toFloat() * 2.54).toString()
                ResultOfBmi((height.toFloat())/100,weight)
            }
            else ->
                Toast.makeText(
                    this@BMICalculatorActivity,
                    "please enter the height in inches or in foot",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun ResultOfBmi(height: Float, weight: Float){
        val bmi = weight/(height*height)
        val bmiValue = BigDecimal(bmi.toDouble()).
        setScale(2,RoundingMode.HALF_EVEN)
        binding?.BmiValueTxt?.text = bmiValue.toString()
        if(bmi.compareTo(15f)<= 0){
            binding?.statusBmiTxt?.text = "Severly Under Weight"
            binding?.descriptionBmiTxt?.text = "Oops! You really need to take a better care of yourself! Eat calories food to gain weight."
        }
        else if(bmi.compareTo(15f)> 0 && bmi.compareTo(18.5f)<=0){
            binding?.statusBmiTxt?.text = "Under Weight"
            binding?.descriptionBmiTxt?.text = "Oops! You really need to take a better care of yourself! Eat calories food to gain weight."
        }
        else if(bmi.compareTo(18.5f)> 0 && bmi.compareTo(25f)<=0){
            binding?.statusBmiTxt?.text = "Normal"
            binding?.descriptionBmiTxt?.text = "Thats cool.  Your weight is normal. Keep going like this"
        }
        else if(bmi.compareTo(25f)> 0 && bmi.compareTo(30f)<=0){
            binding?.statusBmiTxt?.text = "OverWeight"
            binding?.descriptionBmiTxt?.text = "Oops! You need to take care of yourself."
        }
        else{
            binding?.statusBmiTxt?.text = "Obese class | (Severly obese)"
            binding?.descriptionBmiTxt?.text = "Oops! You really need to take care of yourself. Act Now!!"
        }
        binding?.yourBmiTxt?.visibility=View.VISIBLE
        binding?.BmiValueTxt?.visibility = View.VISIBLE
        binding?.statusBmiTxt?.visibility = View.VISIBLE
        binding?.descriptionBmiTxt?.visibility = View.VISIBLE

    }

}