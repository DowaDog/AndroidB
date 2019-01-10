package com.takhyungmin.dowadog.modifyinfoanimal.model

import android.util.Log
import com.takhyungmin.dowadog.modifyinfoanimal.model.get.GETModifyInfoAnimalResponse
import com.takhyungmin.dowadog.modifyinfoanimal.model.put.PUTModifyInfoAnimalResponse
import com.takhyungmin.dowadog.mypage.model.MypageSettingObject
import com.takhyungmin.dowadog.mypage.model.put.PUTMypageSettingResponse
import com.takhyungmin.dowadog.utils.ApplicationData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.Part
import retrofit2.http.Path

class ModifyInfoAnimalModel {

    private var modifyInfoNetworkService: ModifyInfoNetworkService

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        modifyInfoNetworkService = retrofit.create(ModifyInfoNetworkService::class.java)
    }

    fun getModifyData(animalid: Int) {

        modifyInfoNetworkService.getModifyInfoData(ApplicationData.auth, animalid)
                .enqueue(object : Callback<GETModifyInfoAnimalResponse> {

                    override fun onFailure(call: Call<GETModifyInfoAnimalResponse>?, t: Throwable?) {
                        Log.e("modifyInfo통신 실패", t.toString())
                    }

                    override fun onResponse(call: Call<GETModifyInfoAnimalResponse>?, response: Response<GETModifyInfoAnimalResponse>?) {

                        Log.v("TAGG", response.toString())
                        Log.v("TAGG", response!!.body().toString())

                        response?.takeIf { it.isSuccessful }
                                ?.body()
                                ?.let {
                                    ModifyInfoAnimalObject.modifyInfoAnimalActivityPresenter.GETresponseData(it)
                                    //Log.v("TAGG","모델 리스폰스")

                                }
                    }
                })
    }

    fun putAnimalData(animalid: Int, name: String, gender : String, kind : String, weight: String,
                      neuterYn: Boolean, age: String, img: MultipartBody.Part?, inoculation : ArrayList<String>) {

        var name = RequestBody.create(MediaType.parse("text/plain"), name)
        var weight = RequestBody.create(MediaType.parse("text/plain"), weight)
        var neuterYn = RequestBody.create(MediaType.parse("text/plain"), neuterYn.toString())
        var age = RequestBody.create(MediaType.parse("text/plain"), age)

        var innoString = ""
        inoculation.forEach {
            innoString += (it + ", ")
        }
        //val inoArray =

        var inoculationArray = RequestBody.create(MediaType.parse("text/plain"), innoString)

        var gender = RequestBody.create(MediaType.parse("text/plain"), name.toString())
        var kind = RequestBody.create(MediaType.parse("text/plain"), weight.toString())


        modifyInfoNetworkService.putModifyAnimalData(ApplicationData.auth, animalid, name, gender, kind, weight, neuterYn, img, age, inoculationArray)
                .enqueue(object : Callback<PUTModifyInfoAnimalResponse> {
                    override fun onFailure(call: Call<PUTModifyInfoAnimalResponse>, t: Throwable) {
                        Log.v("modifyInfoAnimal 통신 실패!", t.toString())
                    }

                    override fun onResponse(call: Call<PUTModifyInfoAnimalResponse>?, response: Response<PUTModifyInfoAnimalResponse>?) {

                        Log.v("TAGG", response.toString())
                        Log.v("TAGG", response!!.body().toString())

                        response?.takeIf { it.isSuccessful }
                                ?.body()
                                ?.let {
                                    ModifyInfoAnimalObject.modifyInfoAnimalActivityPresenter.PUTresponseData(it)

                                    Log.v("TAGG", "통신성공 야호!")
                                }
                    }
                })
    }

}