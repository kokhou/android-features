package com.example.myapplication.retrofit

import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>

    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: String): Response<Albums>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path("id") albumId: Int): Response<AlbumsItem>

    @POST("/albums")
    suspend fun updateAlbum(@Body album: AlbumsItem): Response<AlbumsItem>

}