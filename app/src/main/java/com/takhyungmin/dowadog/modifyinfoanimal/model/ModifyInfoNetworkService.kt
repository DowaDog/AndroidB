package com.takhyungmin.dowadog.modifyinfoanimal.model

import com.takhyungmin.dowadog.modifyinfoanimal.model.get.GETModifyInfoAnimalResponse
import com.takhyungmin.dowadog.modifyinfoanimal.model.put.PUTModifyInfoAnimalResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ModifyInfoNetworkService {
    //동물들 상세보기
    @GET("api/normal/mypage/adoptAnimals/{adoptAnimalId}")
    fun getModifyInfoData(
            @Header("Authorization") authorization: String,
            @Path("adoptAnimalId") adoptAnimalId : Int
    ): Call<GETModifyInfoAnimalResponse>

    @Multipart
@PUT("api/normal/mypage/adoptAnimals/{adoptAnimalId}")
fun putModifyAnimalData(
            @Header("Authorization") authorization: String,
            @Path("adoptAnimalId") adoptAnimalId: Int,
            @Part("name") name: RequestBody,
            @Part("gender") gender: RequestBody,
            @Part("kind") kind : RequestBody,
            @Part("weight") weight: RequestBody,
            @Part("neuterYn") neuterYn: RequestBody,
            @Part profileImgFile: MultipartBody.Part?,
            @Part("age") age : RequestBody,
            @Part("inoculationArray") inoculationArray: RequestBody
    ) : Call<PUTModifyInfoAnimalResponse>
}