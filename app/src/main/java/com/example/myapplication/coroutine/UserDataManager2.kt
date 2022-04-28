package com.example.myapplication.coroutine

import android.util.Log
import kotlinx.coroutines.*

class UserDataManager2 {

    var count = 0
    lateinit var deferred: Deferred<Int>
    suspend fun getTotalUserCount(): Int {

        coroutineScope {
            Log.i("tag", "hi 1")
            launch(Dispatchers.IO) {
                delay(3000)
                count = 50
                Log.i("tag", "hi 2")
            }
            Log.i("tag", "hi 3")
            deferred = async(Dispatchers.IO) {
                delay(4000)
                Log.i("tag", "hi 4")
                return@async 30
            }

        }

        return count + deferred.await()
    }
}