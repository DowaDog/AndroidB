package com.takhyungmin.dowadog.login.model

import android.util.Log
import com.takhyungmin.dowadog.login.model.get.GetLoginResponse
import com.takhyungmin.dowadog.login.model.post.PostLoginDTO
import com.takhyungmin.dowadog.login.model.post.PostRefreshResponse
import com.takhyungmin.dowadog.signup.SignObject
import com.takhyungmin.dowadog.utils.ApplicationData
import io.reactivex.Observable
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginModel {
    private var loginNetworkService : LoginNetworkService

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        loginNetworkService = retrofit.create(LoginNetworkService::class.java)
    }

    fun postLoginFromSign(postLogin : PostLoginDTO){
        loginNetworkService.postLogin(postLogin).enqueue(object : Callback<GetLoginResponse>{
            override fun onFailure(call: Call<GetLoginResponse>, t: Throwable) {
               Log.v("loginNetwork", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetLoginResponse>, response: Response<GetLoginResponse>) {
                if(response.isSuccessful){
                    Observable.just(response.body()!!.data)
                            .subscribe { it ->  SignObject.signIdSettingActivityPresenter.responseLoginFromSign(it)}

                }else{

                }
            }

        })
    }

    fun postRefresh(refresh : String){
        loginNetworkService.postRefresh(refresh).enqueue(object : Callback<PostRefreshResponse> {
            override fun onFailure(call: Call<PostRefreshResponse>, t: Throwable) {
                Log.v("loginNetwork", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<PostRefreshResponse>, response: Response<PostRefreshResponse>) {
                if (response.isSuccessful) {
                    Observable.just(response.body()!!.data)
                            .subscribe { it -> LoginObject.loginActivityPresenter.responseRefresh(it) }

                } else {

                }
            }
        })
    }

    fun requestLogin(id : String, pwd : String){
        loginNetworkService.postLogin(PostLoginDTO(id, pwd)).enqueue(object : Callback<GetLoginResponse>{
            override fun onFailure(call: Call<GetLoginResponse>, t: Throwable) {
                Log.v("loginNetwork", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetLoginResponse>, response: Response<GetLoginResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.data != null) {
                        Observable.just(response.body()!!.data)
                                .subscribe { it ->  LoginObject.loginActivityPresenter.responseLogin(it)}
                    }else{
                        LoginObject.loginActivityPresenter.toast()
                    }
                }
            }

        })
    }
}