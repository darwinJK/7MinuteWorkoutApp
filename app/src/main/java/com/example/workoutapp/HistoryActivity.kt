package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {

     private var binding : ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val historyDao = (application as ExerciseApp).database.historyDao()

        setSupportActionBar(binding?.toolbarHistoryActivity)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

       lifecycleScope.launch {
           historyDao.fetAllData().collect{
               val list = ArrayList(it)
               setupRecyclerViewForHistory(list)
           }
       }
    }

    private fun setupRecyclerViewForHistory(list: ArrayList<exerciseHistory>, ) {
        if(list.isNotEmpty()){
            val historyAdapter = HistoryAdapter(list)
            binding?.historyRc?.layoutManager = LinearLayoutManager(this)
            binding?.historyRc?.adapter = historyAdapter
            binding?.historyRc?.visibility= View.VISIBLE
            binding?.noRecordsHistory?.visibility = View.GONE
        }else{
            binding?.historyRc?.visibility= View.GONE
            binding?.noRecordsHistory?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding= null
    }
}