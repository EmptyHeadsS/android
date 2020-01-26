package com.example.myapplication.ui.main

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface CommunicationService {
    @GET("/login")
    fun getUserConfirmation(@Query("username") username: String,
                            @Query("password") password: String): Call<String>

    fun getTrackIdsFor(@Query("username") username: String,
                       @Query("size") size: String): Call<List<SongId>>
}