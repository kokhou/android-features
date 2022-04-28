package com.example.myapplication.recycler

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRecycleBinding
import com.example.recyclerviewdemo.Fruit

class RecycleActivity : AppCompatActivity() {

    val fruitsList = listOf(
        Fruit("Mango", "Joe"),
        Fruit("Apple", "Frank"),
        Fruit("Banana", "Tom"),
        Fruit("Guava", "Joe"),
        Fruit("Lemon", "Alex"),
        Fruit("Pear", "Joe"),
        Fruit("Orange", "Alex")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setBackgroundColor(Color.YELLOW)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyRecyclerViewAdapter(
            fruitsList,
        ) { selectedItem: Fruit ->
            listItemClicked(selectedItem)
        }
    }

    private fun listItemClicked(fruit: Fruit) {
        Toast.makeText(
            this@RecycleActivity,
            "Supplier is : ${fruit.supplier}",
            Toast.LENGTH_LONG
        ).show()
    }

}