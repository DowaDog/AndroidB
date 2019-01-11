package com.takhyungmin.dowadog.contents.model

import android.util.Log
import com.takhyungmin.dowadog.contents.ContentsObject
import com.takhyungmin.dowadog.contents.model.get.GetEduContentsResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import io.reactivex.Observable
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContentsModel {
    private var contentsNetworkService : ContentsNetworkService

    init{
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        contentsNetworkService = retrofit.create(ContentsNetworkService::class.java)
    }

    fun requestList(){
        Log.v("들어옴2", "들어옴2")
        contentsNetworkService.getEduContentsList(ApplicationData.auth).enqueue(object : Callback<GetEduContentsResponse>{
            override fun onFailure(call: Call<GetEduContentsResponse>, t: Throwable) {
                Log.v("ConetnstList", t.toString())
                Log.v("들어옴3", "들어옴3")
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetEduContentsResponse>, response: Response<GetEduContentsResponse>) {
                if(response.isSuccessful){
                    Log.v("들어옴4", "들어옴4")
                    Log.v("ConetnstList", response.body()!!.data.content[0].title)
                    Observable.just(response.body()!!.data.content)
                            .subscribe { contents -> ContentsObject.contentsEduFragmentPresenter.responseList(contents) }
                }else{
                    Log.v("들어옴5", "들어옴5")
                    Log.v("ConetnstList", response.body()!!.message)

                }
            }
        })
    }

    fun requestSenseList(){
        contentsNetworkService.getSenseContentsList(ApplicationData.auth, 0, 20).enqueue(object : Callback<GetEduContentsResponse>{
            override fun onFailure(call: Call<GetEduContentsResponse>, t: Throwable) {
                Log.v("ConetnstList", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetEduContentsResponse>, response: Response<GetEduContentsResponse>) {
                if(response.isSuccessful){
                    Observable.just(response.body()!!.data.content)
                            .subscribe { contents -> ContentsObject.contentsSenseFragmentPresenter.responseData(contents) }
                }
            }
        })
    }

}