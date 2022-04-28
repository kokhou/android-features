package com.example.myapplication.coroutine

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineActivity : AppCompatActivity() {
    private var count = 0

    private lateinit var binding: ActivityCoroutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine)

        binding.btnCount.setOnClickListener {
//            binding.tvCount.text = count++.toString()
//            startActivity(Intent(this@CoroutineActivity, AsyncAwaitActivity::class.java))
        }

        binding.btnDownloadUserData.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                binding.tvCount.text = UserDataManager2().getTotalUserCount().toString()
            }
        }
    }

    private suspend fun downloadUserData() {
        for (i in 1..200000) {
            withContext(Dispatchers.Main) {
                "Downloading user $i in ${Thread.currentThread().name}".also {
                    binding.tvCount.text = it
                }
            }
        }
    }
}
