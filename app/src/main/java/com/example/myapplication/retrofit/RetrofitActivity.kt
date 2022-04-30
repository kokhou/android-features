package com.example.myapplication.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRetrofitBinding
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetrofitBinding
    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit)
        retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        uploadAlbum()
    }

    fun getRequestWithQueryParameters() {
        // path parameter example
        val pathResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbum(3)
            emit(response)
        }

        pathResponse.observe(this) {
            binding.textView5.text = it.body()?.toString() ?: ""
        }
    }

    fun getRequestWithPathParameters() {
        // Path example
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getSortedAlbums("1")
            emit(response)
        }

        responseLiveData.observe(this) {
            val albumsList = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    Log.i("Tag", albumsItem.title)
                    binding.textView5.append(albumsItem.toString() + "\n\n")
                }
            }
        }
    }

    private fun uploadAlbum() {
        val albums = AlbumsItem(0, "This is my kok hou title", 100)

        // Path example
        val postResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.updateAlbum(albums)
            emit(response)
        }

        postResponse.observe(this) {
            val uploadedAlbum = it.body()?.toString() ?: ""
            binding.textView5.text = uploadedAlbum
        }

    }
}