package com.takhyungmin.dowadog.apply.online.model.get

import com.takhyungmin.dowadog.dogdetail.model.post.PostDogDetailHeartResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApplyOnlineFouthNetwork {

    // 온라인 신청
    @Multipart
    @POST("api/normal/registrations/online")
    fun postOnlineAdoptResponse(
            @Header("Authorization") authorization : String,
            @Part("phone") phone : RequestBody,
            @Part("email") email : RequestBody,
            @Part("address") address : RequestBody,
            @Part("job") job : RequestBody,
            @Part("havePet") havePet : Boolean,
            @Part("regType") regType : RequestBody,
            @Part("petInfo") petInfo : RequestBody,
            @Part("adoptType") adoptType : RequestBody,
            @Part("animalId") animalId : Int,
            @Part animalImg : MultipartBody.Part?
    ) : Call<PostDogDetailHeartResponse>
}