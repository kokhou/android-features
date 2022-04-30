package com.example.myapplication.db.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ListItemDbBinding
import com.example.myapplication.db.db.Subscriber

class DbRecyclerViewAdapter(
    private val clickListener: (Subscriber) -> Unit
) :
    RecyclerView.Adapter<DbRecyclerViewHolder>() {

    private val subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DbRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ListItemDbBinding>(
            layoutInflater,
            R.layout.list_item_db,
            parent,
            false
        )

        return DbRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DbRecyclerViewHolder, position: Int) {
        holder.bind(subscriber = subscribers[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    fun setList(newSubscribers: List<Subscriber>) {
        subscribers.clear()
        subscribers.addAll(newSubscribers)
    }

}

class DbRecyclerViewHolder(val binding: ListItemDbBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.listItemView.setOnClickListener {
            clickListener(subscriber)
        }
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email

    }
}