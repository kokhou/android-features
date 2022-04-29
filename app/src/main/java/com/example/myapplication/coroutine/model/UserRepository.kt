package com.example.myapplication.coroutine.model

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserRepository {

    var list = listOf<User>()

    suspend fun getUsers(): List<User> {
        delay(3000)
        list = listOf(
            User(1, "kokhou"),
            User(2, "kokhoua"),
            User(3, "kokhoub"),
            User(4, "kokhouc"),
        )

        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            list = listOf(
                User(5, "kokhou2"),
                User(6, "kokhoua3"),
                User(7, "kokhoub4"),
                User(8, "kokhouc5"),
            )
        }

        return list
    }

    suspend fun getUsers2(): List<User> {
        delay(6000)
        list = listOf(
            User(5, "kokhou2"),
            User(6, "kokhoua3"),
            User(7, "kokhoub4"),
            User(8, "kokhouc5"),
        )

        return list
    }
}