package com.takhyungmin.dowadog.pressedadopt.model

import android.util.Log
import com.takhyungmin.dowadog.pressedadopt.model.get.PressedAdoptNetwork
import com.takhyungmin.dowadog.pressedadopt.model.post.PostDirectAdoptRequest
import com.takhyungmin.dowadog.utils.ApplicationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PressedAdoptModel{
    private var pressedAdoptNetwork : PressedAdoptNetwork

    init{
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        pressedAdoptNetwork = retrofit.create(PressedAdoptNetwork::class.java)
    }

    fun postDirectAdoptRequest(animalId : Int){
        pressedAdoptNetwork.postDirectAdoptRequest(ApplicationData.auth,
                PostDirectAdoptRequest(animalId)).enqueue(object : Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Log.v("입양 신청 완료", "입양 신청 완료")
                }
            }
        })
    }
}