package com.takhyungmin.dowadog.adoptedanimal.model

import com.takhyungmin.dowadog.adoptedanimal.model.get.GETAdoptedAnimalResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface AdoptedAnimalNetworkService {

    @GET("api/normal/mypage/adoptAnimals")
    fun getAdoptedAnimal(
            @Header("Authorization") authorization: String
    ): Call<GETAdoptedAnimalResponse>
}