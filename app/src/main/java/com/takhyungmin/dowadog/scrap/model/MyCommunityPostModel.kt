package com.takhyungmin.dowadog.scrap.model

import android.util.Log
import com.takhyungmin.dowadog.scrap.model.get.GetMyCommunityPostResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyCommunityPostModel {
    private var myCommunityPostNetwork: MyCommunityPostNetwork

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        myCommunityPostNetwork = retrofit.create(MyCommunityPostNetwork::class.java)
    }

    fun getMyCommunityPost() {

        myCommunityPostNetwork.getMyCommunityPostList(ApplicationData.auth).enqueue(object : Callback<GetMyCommunityPostResponse>{
            override fun onFailure(call: Call<GetMyCommunityPostResponse>?, t: Throwable?) {
                Log.e("getMyCommunityPost통신실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetMyCommunityPostResponse>?, response: Response<GetMyCommunityPostResponse>?) {

                Log.v("TAGGGG", response!!.body().toString())
                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            MyCommunityPostObject.myCommunityPostActivityPresenter.requestMyCommunityPostList(it)
                        }
            }
        })
    }
}