package com.example.myapplication.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myapplication.coroutine.model.UserRepository
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel2 : ViewModel() {
    private var userRepository = UserRepository()
    var users = liveData(Dispatchers.IO) {
        emit(userRepository.getUsers())
    }

//    var users: MutableLiveData<List<User>> = MutableLiveData()
//
//    fun getUserData() {
//        viewModelScope.launch {
//            var result: List<User>
//            withContext(Dispatchers.IO) {
//                result = userRepository.getUsers()
//            }
//            users.value = result
//        }
//    }
}