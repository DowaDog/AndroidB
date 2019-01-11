package com.takhyungmin.dowadog.letter.model.get

import android.util.Log
import com.takhyungmin.dowadog.letter.model.LetterNetworkService
import com.takhyungmin.dowadog.letter.model.LetterObject
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LetterModel {

    private var letterNetworkService: LetterNetworkService


    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        letterNetworkService = retrofit.create(LetterNetworkService::class.java)
    }

    fun getMypageData() {

        letterNetworkService.getLetterList(ApplicationData.auth)
                .enqueue(object : Callback<GETLetterActivityResponse> {

                    override fun onFailure(call: Call<GETLetterActivityResponse>?, t: Throwable?) {
                        Log.e("mypage통신 실패", t.toString())
                        if (t.toString().contains("Failed to connect to")) {
                            ApplicationData.applicationContext.toast("점검 중입니다.")
                        }

                        if (t.toString().contains("Unable to resolve host")) {
                            ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                        }
                    }

                    override fun onResponse(call: Call<GETLetterActivityResponse>?, response: Response<GETLetterActivityResponse>?) {

                        Log.v("TAGG",response.toString())
                        Log.v("TAGG",response!!.body().toString())

                        response?.takeIf { it.isSuccessful }
                                ?.body()
                                ?.let {
                                    LetterObject.letterActivityPresenter.responseData(it)
                                    //Log.v("TAGG","모델 리스폰스")

                                }
                    }
                })
    }

    fun getReadLetterData() {

        letterNetworkService.getReadLetterList(ApplicationData.auth).enqueue(object: Callback<GetReadLetterResponse>{
            override fun onFailure(call: Call<GetReadLetterResponse>?, t: Throwable?) {
                Log.e("편지 통신 실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }

            }

            override fun onResponse(call: Call<GetReadLetterResponse>?, response: Response<GetReadLetterResponse>?) {
                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            LetterObject.letterActivityPresenter.reponseReadLetterData(it)
                        }
            }
        })
    }



}