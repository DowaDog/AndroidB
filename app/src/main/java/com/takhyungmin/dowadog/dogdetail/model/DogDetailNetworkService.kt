package com.takhyungmin.dowadog.dogdetail.model

import com.takhyungmin.dowadog.dogdetail.model.get.GetDogDetailResponse
import com.takhyungmin.dowadog.dogdetail.model.post.PostDogDetailHeartResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DogDetailNetworkService {

    // 커뮤니티 포스트 디테일 보기
    @GET("api/normal/animals/{animalId}")
    fun GetDogDetailResponse(
            @Header("Authorization") auth : String,
            @Path("animalId") animalId: Int
    ): Call<GetDogDetailResponse>

    @POST("api/normal/animals/{animalId}/likes")
    fun postDogDetailHeart(
            @Header("Authorization") authorization: String,
            @Path("animalId") animalId: Int): Call<PostDogDetailHeartResponse>
}

