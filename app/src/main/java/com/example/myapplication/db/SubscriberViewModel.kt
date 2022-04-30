package com.example.myapplication.db

import android.util.Patterns
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

        if (inputName.value.isNullOrBlank()) {
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value.isNullOrBlank()) {
            statusMessage.value = Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
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


    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber Insert Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        val updateRowId = repository.update(subscriber)
        if (updateRowId > -1) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Subscriber Updated Successfully $updateRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        val deleteRowId = repository.delete(subscriber)
        if (deleteRowId > -1) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Subscriber Delete Successfully $deleteRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun clearAll() = viewModelScope.launch {
        val deleteAllId = repository.deleteAll()
        if (deleteAllId > -1) {
            statusMessage.value = Event("Subscriber Delete All Successfully $deleteAllId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
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