package com.example.myapplication.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLifecycleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LifecycleDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLifecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lifecycle)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            Log.i("tag", "launch")
        }

        lifecycleScope.launchWhenCreated {
            Log.i("tag", "launchWhenCreated")
        }
        lifecycleScope.launchWhenResumed {
            Log.i("tag", "launchWhenResumed")
        }
        lifecycleScope.launchWhenStarted {
            Log.i("tag", "launchWhenStarted")
        }
    }
}