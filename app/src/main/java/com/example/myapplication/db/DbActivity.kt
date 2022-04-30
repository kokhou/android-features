package com.example.myapplication.db

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDbBinding
import com.example.myapplication.db.adapter.DbRecyclerViewAdapter
import com.example.myapplication.db.db.Subscriber
import com.example.myapplication.db.db.SubscriberDatabase
import com.example.myapplication.db.db.SubscriberRepository

class DbActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDbBinding
    private lateinit var viewModel: SubscriberViewModel
    private lateinit var adapter: DbRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_db)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        viewModel.message.observe(this) { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DbRecyclerViewAdapter { selectedItem -> listItemClicked(selectedItem) }
        binding.subscriberRecyclerView.adapter = adapter

        displaySubscriberList()
    }

    private fun displaySubscriberList() {
        viewModel.subscribers.observe(this) {
            Log.i("Tag", it.toString())
            adapter.apply {
                setList(it)
                notifyDataSetChanged()
            }
        }
    }

    private fun listItemClicked(subscriber: Subscriber) {
        Log.i("Tag", "${subscriber.name}")
//        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()
        viewModel.initUpdateAndDelete(subscriber)
    }
}