package com.takhyungmin.dowadog.scrap.scrapmodel

import android.util.Log
import com.takhyungmin.dowadog.scrap.scrapmodel.get.GetMyScrapResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScrapModel  {
    private var scrapNetwork: ScrapNetwork

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        scrapNetwork = retrofit.create(ScrapNetwork::class.java)
    }

    fun getMyScrapList(){

        scrapNetwork.getScrapList(ApplicationData.auth).enqueue(object: Callback<GetMyScrapResponse>{
            override fun onFailure(call: Call<GetMyScrapResponse>?, t: Throwable?) {
                Log.e("getMyScrapList통신실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetMyScrapResponse>?, response: Response<GetMyScrapResponse>?) {
                Log.v("TAGGGG", response!!.body().toString())
                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            ScrapObject.scrapActivityPresenter.responseMyScrapList(it)
                            // MyCommunityPostObject.myCommunityPostActivityPresenter.requestMyCommunityPostList(it)
                        }
            }
        })
    }
}