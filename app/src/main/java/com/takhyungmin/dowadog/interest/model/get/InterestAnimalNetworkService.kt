package com.takhyungmin.dowadog.interest.model.get

import com.takhyungmin.dowadog.mypage.model.get.GETMypageSettingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface InterestAnimalNetworkService {

    @GET("api/normal/mypage/likes")
    fun getInterestAnimal(
            @Header("Authorization") authorization: String
    ): Call<GETInterestAnimalResponse>
}