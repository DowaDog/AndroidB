package com.takhyungmin.dowadog.pressedadopt.model

import android.util.Log
import com.takhyungmin.dowadog.pressedadopt.model.get.PressedAdoptNetwork
import com.takhyungmin.dowadog.pressedadopt.model.post.PostDirectAdoptRequest
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
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
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Log.v("입양 신청 완료", "입양 신청 완료")
                }
            }
        })
    }
}