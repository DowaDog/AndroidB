package com.takhyungmin.dowadog.interest.model.get

import android.util.Log
import com.takhyungmin.dowadog.interest.model.InterestAnimalObject
import com.takhyungmin.dowadog.utils.ApplicationData
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