package com.takhyungmin.dowadog.interest.model.get

import android.util.Log
import com.takhyungmin.dowadog.interest.model.InterestAnimalObject
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InterestAnimalModel {

    private var interestAnimalNetworkService: InterestAnimalNetworkService

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        interestAnimalNetworkService = retrofit.create(InterestAnimalNetworkService::class.java)
    }

    fun getInterestAnimal() {

        interestAnimalNetworkService.getInterestAnimal(ApplicationData.auth)
                .enqueue(object : Callback<GETInterestAnimalResponse> {
                    override fun onFailure(call: Call<GETInterestAnimalResponse>?, t: Throwable?) {
                        Log.e("TAGG", "interest통신 실패")
                        Log.e("interest통신 실패", t.toString())
                        if (t.toString().contains("Failed to connect to")) {
                            ApplicationData.applicationContext.toast("점검 중입니다.")
                        }

                        if (t.toString().contains("Unable to resolve host")) {
                            ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                        }
                    }

                    override fun onResponse(call: Call<GETInterestAnimalResponse>?, response: Response<GETInterestAnimalResponse>?) {

                        Log.v("TAGG", response.toString())
                        Log.v("TAGG", response!!.body().toString())

                        response?.takeIf { it.isSuccessful }
                                ?.body()
                                ?.let {
                                    InterestAnimalObject.interestAnimalActivityPresenter.responseData(it)
                                    Log.v("TAGG","Interest 모델 리스폰스")

                                }
                    }
                })
    }
}