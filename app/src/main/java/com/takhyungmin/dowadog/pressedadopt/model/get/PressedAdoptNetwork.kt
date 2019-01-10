package com.takhyungmin.dowadog.pressedadopt.model.get

import com.takhyungmin.dowadog.pressedadopt.model.post.PostDirectAdoptRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PressedAdoptNetwork {
    @POST("api/normal/registrations/offline")
    fun postDirectAdoptRequest(
            @Header("Authorization") auth : String,
            @Body adopt : PostDirectAdoptRequest) : Call<Unit>
}