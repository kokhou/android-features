package com.example.myapplication.coroutine

import kotlinx.coroutines.*

class UserDataManager {

    suspend fun getTotalUserCount(): Int {
        var count = 0
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            count = 50
        }

        val result =  CoroutineScope(Dispatchers.IO).async {
            delay(3000)
            return@async 70
        }.await()

        return count + result
    }
}