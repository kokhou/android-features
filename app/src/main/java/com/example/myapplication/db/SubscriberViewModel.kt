package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.db.Subscriber
import com.example.myapplication.db.db.SubscriberRepository
import com.example.myapplication.db.event.Event
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(Subscriber(0, name, email))

            inputName.value = ""
            inputEmail.value = ""
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        repository.insert(subscriber)
        statusMessage.value = Event("Subscriber Insert Successfully.")
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        repository.update(subscriber)
        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("Subscriber Updated Successfully.")
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("Subscriber Delete Successfully.")
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("Subscriber Delete All Successfully.")
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }
}