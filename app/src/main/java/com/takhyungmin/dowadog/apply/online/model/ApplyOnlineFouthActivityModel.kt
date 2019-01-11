package com.takhyungmin.dowadog.apply.online.model

import android.util.Log
import com.takhyungmin.dowadog.apply.online.model.get.ApplyOnlineFouthNetwork
import com.takhyungmin.dowadog.dogdetail.model.post.PostDogDetailHeartResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplyOnlineFouthActivityModel {
    private var applyOnlineFouthNetwork: ApplyOnlineFouthNetwork

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        applyOnlineFouthNetwork = retrofit.create(ApplyOnlineFouthNetwork::class.java)
    }

    fun requestApplyOnlineFouthResponse(phoneNum: String, email: String, address: String, job: String, havePet: Boolean,
                                        regType: String, petInfo: String, adoptType: String, animalId: Int, animalImg: MultipartBody.Part?) {
        val phoneNum = RequestBody.create(MediaType.parse("text/plain"), phoneNum)
        val email = RequestBody.create(MediaType.parse("text/plain"), email)
        val address = RequestBody.create(MediaType.parse("text/plain"), address)
        val job = RequestBody.create(MediaType.parse("text/plain"), job)
        val regType = RequestBody.create(MediaType.parse("text/plain"), regType)
        val petInfo = RequestBody.create(MediaType.parse("text/plain"), petInfo)
        val adoptType = RequestBody.create(MediaType.parse("text/plain"), adoptType)
        Log.v("thm" ,"들어옴")
        Log.v("thm" ,animalImg.toString())

        applyOnlineFouthNetwork.postOnlineAdoptResponse(ApplicationData.auth, phoneNum, email,address,job,havePet
                ,regType, petInfo, adoptType, animalId, animalImg).enqueue(object: Callback<PostDogDetailHeartResponse>{
            override fun onFailure(call: Call<PostDogDetailHeartResponse>?, t: Throwable?) {
                Log.e("리퀘스트온라인어플라이통신실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }
            override fun onResponse(call: Call<PostDogDetailHeartResponse>?, response: Response<PostDogDetailHeartResponse>?) {
                Log.v("thm" ,response.toString())
                Log.v("thm" ,response!!.body().toString())
                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            ApplyOnlineFouthObject.applyOnlineFouthActivityPresenter.responseData(it)
                        }
            }
        })
    }
}