package com.takhyungmin.dowadog.signup.model.get

import android.util.Log
import com.takhyungmin.dowadog.signup.SignObject
import com.takhyungmin.dowadog.signup.model.SignInfoEmailNetworkService
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignInfoEmailModel {
    private var signInfoEmailNetworkService : SignInfoEmailNetworkService

    init {

        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        signInfoEmailNetworkService = retrofit.create(SignInfoEmailNetworkService::class.java)
    }

    fun getSignInfoWriteData(email: String){
        signInfoEmailNetworkService.GetSignInfoEmailResponse("tae@gmail.com")
                .enqueue(object: Callback<GetSignInfoEmailResponse> {
            override fun onFailure(call: Call<GetSignInfoEmailResponse>?, t: Throwable?) {
                Log.e("SignInfo 이메일 중복확인 통신실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<GetSignInfoEmailResponse>?, response: Response<GetSignInfoEmailResponse>?) {

                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            SignObject.SignInfoWriteActivityPresenter.responseData(it)
                        }
            }
        })
    }


}