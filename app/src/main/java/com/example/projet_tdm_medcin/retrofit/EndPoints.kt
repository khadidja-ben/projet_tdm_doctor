package com.example.projet_tdm.retrofit


import com.example.projet_tdm_medcin.entity.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EndPoints {

    @GET("/doctorAuth/{phone}/{password}")
    fun authentification(@Path ("phone") phone:String, @Path("password")password: String): Call<Doctor>

}