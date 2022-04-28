package com.example.myapplication.coroutine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.MainActivity2
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.*

class AsyncAwaitActivity : AppCompatActivity() {
    private var count = 0

    private lateinit var binding: ActivityCoroutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine)

        binding.btnCount.setOnClickListener {
            for (i in 1..30000) {
                Log.i("Hi", "$i")
                CoroutineScope(Dispatchers.Main).launch {
                    binding.tvCount.text = i.toString()
                }
            }
        }
        binding.btnDownloadUserData.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.i("Hi", "click")

                val stock1 = async(Dispatchers.IO) { getStock1() }
                val stock2 = async(Dispatchers.IO) { getStock2() }
                val stock3 = async(Dispatchers.IO) { getStock3() }
                val stock4 = async(Dispatchers.IO) { getStock4() }

                val (data1, data2, data3, data4) = awaitAll(stock1, stock2, stock3, stock4)

                Toast.makeText(applicationContext, data1 + data2 + data3 + data4, Toast.LENGTH_LONG)
                    .show()
                Log.i("Hi", data1 + data2 + data3 + data4)

            }
        }
    }

    private suspend fun getStock1(): String {
        delay(5000)
        Log.i("Tag", "stock 1 ")
        return "1"
    }

    private suspend fun getStock2(): String {
        delay(8000)
        Log.i("Tag", "stock 2 ")
        return "2"
    }

    private suspend fun getStock3(): String {
        delay(6000)
        Log.i("Tag", "stock 3 ")
        return "3"
    }

    private suspend fun getStock4(): String {
        delay(10000)
        Log.i("Tag", "stock 4 ")
        return "4"
    }

}
