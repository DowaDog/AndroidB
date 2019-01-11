package com.takhyungmin.dowadog.contents.model.get

import android.util.Log
import com.takhyungmin.dowadog.contents.model.ContentEduDetailObject
import com.takhyungmin.dowadog.contents.model.ContentsEduDetailNetworkService
import com.takhyungmin.dowadog.contents.model.ContentsSenseDetailObject
import com.takhyungmin.dowadog.contents.model.post.PostScrapResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContentsEduDetailModel {
    private var contentsEduDetailNetworkService: ContentsEduDetailNetworkService

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        contentsEduDetailNetworkService = retrofit.create(ContentsEduDetailNetworkService::class.java)
    }

    fun getContentsEduDetailData(id : Int) {

        contentsEduDetailNetworkService.getContentsEduDetailList(ApplicationData.auth,
                id,10,0)
                .enqueue(object : Callback<GETContentsEduDetailResponse> {

                    override fun onFailure(call: Call<GETContentsEduDetailResponse>?, t: Throwable?) {
                        Log.e("eduDetail통신 실패", t.toString())
                        if (t.toString().contains("Failed to connect to")) {
                            ApplicationData.applicationContext.toast("점검 중입니다.")
                        }

                        if (t.toString().contains("Unable to resolve host")) {
                            ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                        }
                    }

                    override fun onResponse(call: Call<GETContentsEduDetailResponse>?, response: Response<GETContentsEduDetailResponse>?) {

                        response?.takeIf { it.isSuccessful }
                                ?.body()
                                ?.let {
                                    Log.v("message", it.message)
                                    ContentEduDetailObject.contentsEduDetailActivityPresenter.responseData(it)
                                    //Log.v("TAGG","모델 리스폰스")

                                }
                    }
                })
    }

    fun postEduContentsScrap(id : Int){
        contentsEduDetailNetworkService.postScrapEduContents(ApplicationData.auth, id).enqueue(object : Callback<PostScrapResponse>{
            override fun onFailure(call: Call<PostScrapResponse>, t: Throwable) {
                Log.v("EduContents", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<PostScrapResponse>, response: Response<PostScrapResponse>) {
                if(response.isSuccessful){
                    Log.v("EduContents", response.message())
                    if(response.body()!!.message.contains("추가"))
                        ContentEduDetailObject.contentsEduDetailActivityPresenter.responseScrap(true)
                    else
                        ContentEduDetailObject.contentsEduDetailActivityPresenter.responseScrap(false)
                }else{
                    Log.v("EduContents", "fail")
                }
            }
        })
    }

    fun postSenseContentsScrap(id : Int){
        contentsEduDetailNetworkService.postScrapEduContents(ApplicationData.auth, id).enqueue(object : Callback<PostScrapResponse>{
            override fun onFailure(call: Call<PostScrapResponse>, t: Throwable) {
                Log.v("EduContents", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<PostScrapResponse>, response: Response<PostScrapResponse>) {
                if(response.isSuccessful){
                    Log.v("EduContents", response.message())
                    if(response.body()!!.message.contains("추가"))
                        ContentsSenseDetailObject.contentsSenseDetailActivityPresenter.responseScrap(true)
                    else
                        ContentsSenseDetailObject.contentsSenseDetailActivityPresenter.responseScrap(false)
                }else{
                    Log.v("EduContents", "fail")
                }
            }
        })
    }




    fun postEduContentsComplete(id : Int){
        contentsEduDetailNetworkService.postCompleteEduContents(ApplicationData.auth, id).enqueue(object : Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.v("EduContents", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    ContentEduDetailObject.contentsEduDetailActivityPresenter.responseComplete(true)
                }
            }
        })
    }

    //교육 컨텐츠 상식 디테일
    fun getContentsSenseDetailData(id : Int) {

        contentsEduDetailNetworkService.getContentsSenseDetailList(ApplicationData.auth,id)
                .enqueue(object : Callback<ContentSenseDetailResponse> {

                    override fun onFailure(call: Call<ContentSenseDetailResponse>?, t: Throwable?) {
                        if (t.toString().contains("Failed to connect to")) {
                            ApplicationData.applicationContext.toast("점검 중입니다.")
                        }

                        if (t.toString().contains("Unable to resolve host")) {
                            ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                        }
                    }

                    override fun onResponse(call: Call<ContentSenseDetailResponse>?, response: Response<ContentSenseDetailResponse>?) {
                        Log.v("TAGG","모델 리스폰스231123")
                        response?.takeIf { it.isSuccessful }
                                ?.body()
                                ?.let {
                                    Log.v("TAGG", it.message)
                                    ContentsSenseDetailObject.contentsSenseDetailActivityPresenter.responseData(it)
                                    Log.v("TAGG","모델 리스폰스")

                                }
                    }
                })
    }

}