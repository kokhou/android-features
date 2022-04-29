package com.example.myapplication.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCoroutineBinding

class MainActivityCoroutine : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutineBinding
    private lateinit var viewModel: MainActivityViewModel2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine)

        viewModel = ViewModelProvider(this)[MainActivityViewModel2::class.java]
        viewModel.users.observe(this) { myUsers ->
            myUsers.forEach {
                Log.i("tag", "${it.id} + ${it.name}")
            }
        }
    }
}
