package com.takhyungmin.dowadog.home.model

import android.util.Log
import com.takhyungmin.dowadog.home.HomeObject
import com.takhyungmin.dowadog.home.fragment.*
import com.takhyungmin.dowadog.home.model.get.GetDuplicateResponse
import com.takhyungmin.dowadog.home.model.get.GetFragmentResponse
import com.takhyungmin.dowadog.home.model.get.GetUserInfoResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeModel {
    private var homeNetworkSerVice : HomeNetworkService

    init{
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        homeNetworkSerVice = retrofit.create(HomeNetworkService::class.java)
    }


    fun getDuplicateData(id : String){
        homeNetworkSerVice.checkDuplicate(id).enqueue(object : Callback<GetDuplicateResponse> {
            override fun onFailure(call: Call<GetDuplicateResponse>, t: Throwable) {
                Log.v("fail", t.toString())
            }

            override fun onResponse(call: Call<GetDuplicateResponse>, response: Response<GetDuplicateResponse>) {
                if(response.isSuccessful){
                    Observable.just(response.body())
                            .subscribe { s-> HomeObject.homeActivityPresenter.responseData(s!!)}
                }
            }
        })
    }

    fun getUserInfo(){
        homeNetworkSerVice.getUserInfo(ApplicationData.auth).enqueue(object : Callback<GetUserInfoResponse>{
            override fun onFailure(call: Call<GetUserInfoResponse>, t: Throwable) {
                Log.v("fail", t.toString())
            }
            override fun onResponse(call: Call<GetUserInfoResponse>, response: Response<GetUserInfoResponse>) {
                if(response.isSuccessful){
                    HomeObject.homeActivityPresenter.responseUserInfo(response.body()!!.data)
                }
            }
        })
    }

    fun getFragmentState(){
        homeNetworkSerVice.getFragmentState(ApplicationData.auth).enqueue(object : Callback<GetFragmentResponse>{
            override fun onFailure(call: Call<GetFragmentResponse>, t: Throwable) {
                Log.v("fail", t.toString())
            }

            override fun onResponse(call: Call<GetFragmentResponse>, response: Response<GetFragmentResponse>) {
                if(response.isSuccessful){

                    Observable.just(response.body()!!.data)
                            .subscribe { it -> changeFragment(it.view, it.userCheck) }
                    //HomeObject.homeFragmentPresenter.init(response.body()!!.data.view)
                }
            }

        })
    }

    val changeFragment = { view : String, check : Boolean ->
        when(view){
            "NO"->{
                Log.v("success", "in")

                HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentLargeFristSlide())
                //HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentLargeSecondSlide())
                //HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentFirstSlide())
                //HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentSecondSlide())
                //HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentThirdSlide())
                //HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentFourthSlide())
                //HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentFifthSlide())
                //HomeFragmentLargeFristSlide == NO
            }
            "S0"->{
                HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentLargeSecondSlide())
            }
            "S1"->{
                HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentFirstSlide())
            }
            "S2"->{
                HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentSecondSlide())
            }
            "S3"->{
                HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentThirdSlide())
            }
            "COMPLETE"->{
                HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentFourthSlide())
            }
            "DENY"->{
                HomeObject.homeFragmentPresenter.changeIndicator(HomeFragmentFifthSlide())
            }
        }
        HomeObject.homeFragmentPresenter.changeCheck(check)

        //입양 안했을 때(단계시작전) : NO
        //main 1단계 입양신청 : S0
        //main 2단계 전화 상담 : S1
        //main 3단계 직접 방문전 : S2
        //main 3단계 직접 방문후 : S3
        //main 4단계 : COMPLETE
        //단계별로 승인되지 않았을 때 : DENY
    }

    fun postHomeRead(){
        homeNetworkSerVice.postHomeRead(ApplicationData.auth).enqueue(object : Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.v("fail", t.toString())
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Log.v("homeFragment", "success")
                }
            }
        })
    }
}
