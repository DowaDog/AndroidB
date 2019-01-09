package com.takhyungmin.dowadog.apply.online.model.get

import com.takhyungmin.dowadog.dogdetail.model.post.PostDogDetailHeartResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApplyOnlineFouthNetwork {

    //회원가입
    @Multipart
    @POST("api/signup")
    fun postSignIdSettingResponse(
            @Part("phone") phone : RequestBody,
            @Part("email") email : RequestBody,
            @Part("address") address : RequestBody,
            @Part("job") job : RequestBody,
            @Part("havePet") havePet : RequestBody,
            @Part("regType") regType : RequestBody,
            @Part("petInfo") petInfo : RequestBody,
            @Part("adoptType") adoptType : RequestBody,
            @Part("animalId") animalId : RequestBody,
            @Part profileImgFile : MultipartBody.Part?,
            @Part("pushAllow") pushAllow : RequestBody,
            @Part("termsAllow") termsAllow : RequestBody
    ) : Call<PostDogDetailHeartResponse>
}