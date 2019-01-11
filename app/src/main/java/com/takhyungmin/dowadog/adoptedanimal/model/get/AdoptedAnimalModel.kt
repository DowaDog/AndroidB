package com.takhyungmin.dowadog.adoptedanimal.model.get

import android.util.Log
import com.takhyungmin.dowadog.adoptedanimal.model.AdoptedAnimalNetworkService
import com.takhyungmin.dowadog.adoptedanimal.model.AdoptedAnimalObject
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdoptedAnimalModel {
    private var adoptedAnimalNetworkService: AdoptedAnimalNetworkService


    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        adoptedAnimalNetworkService = retrofit.create(AdoptedAnimalNetworkService::class.java)
    }

    fun getAdoptedAnimalList() {

        adoptedAnimalNetworkService.getAdoptedAnimal(ApplicationData.auth)
                .enqueue(object : Callback<GETAdoptedAnimalResponse> {

                    override fun onFailure(call: Call<GETAdoptedAnimalResponse>?, t: Throwable?) {
                        Log.e("mypage통신 실패", t.toString())
                        if (t.toString().contains("Failed to connect to")) {
                            ApplicationData.applicationContext.toast("점검 중입니다.")
                        }

                        if (t.toString().contains("Unable to resolve host")) {
                            ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                        }
                    }

                    override fun onResponse(call: Call<GETAdoptedAnimalResponse>?, response: Response<GETAdoptedAnimalResponse>?) {

                        Log.v("TAGG",response.toString())
                        Log.v("TAGG",response!!.body().toString())

                        response?.takeIf { it.isSuccessful }
                                ?.body()
                                ?.let {
                                    AdoptedAnimalObject.adoptedAnimalActivityPresenter.adoptedResponseData(it)
                                    //Log.v("TAGG","모델 리스폰스")

                                }
                    }
                })
    }
}