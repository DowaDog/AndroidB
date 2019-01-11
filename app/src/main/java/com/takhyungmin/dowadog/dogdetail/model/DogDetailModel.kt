package com.takhyungmin.dowadog.dogdetail.model

import android.util.Log
import com.takhyungmin.dowadog.dogdetail.model.get.GetDogDetailResponse
import com.takhyungmin.dowadog.dogdetail.model.post.PostDogDetailHeartResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogDetailModel {
    private var dogDetailNetworkService: DogDetailNetworkService

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        dogDetailNetworkService = retrofit.create(DogDetailNetworkService::class.java)
    }

    fun getDogDetailData(animalId: Int) {

        dogDetailNetworkService.GetDogDetailResponse(ApplicationData.auth, animalId).enqueue(object : Callback<GetDogDetailResponse> {
            override fun onFailure(call: Call<GetDogDetailResponse>?, t: Throwable?) {
                Log.e("getDogDetailData 통신 실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetDogDetailResponse>?, response: Response<GetDogDetailResponse>?) {
                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            DogDetailObject.dogDetailActivityPresenter.responseData(it)
                        }
            }
        })
    }


    fun postDogDetailHeart(animalId: Int) {

        dogDetailNetworkService.postDogDetailHeart(ApplicationData.auth, animalId).enqueue(object: Callback<PostDogDetailHeartResponse>{
            override fun onFailure(call: Call<PostDogDetailHeartResponse>?, t: Throwable?) {
                Log.e("post개상세하트실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<PostDogDetailHeartResponse>?, response: Response<PostDogDetailHeartResponse>?) {
                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            Log.v("TAGG", it.message)
                        }
            }
        })
    }



}