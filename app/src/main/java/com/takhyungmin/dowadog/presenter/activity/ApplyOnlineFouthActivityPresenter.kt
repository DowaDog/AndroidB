package com.takhyungmin.dowadog.presenter.activity

import android.util.Log
import com.takhyungmin.dowadog.apply.online.ApplyOnlineFourthActivity
import com.takhyungmin.dowadog.apply.online.model.ApplyOnlineFouthActivityModel
import com.takhyungmin.dowadog.dogdetail.model.post.PostDogDetailHeartResponse
import com.takhyungmin.dowadog.presenter.BasePresenter
import okhttp3.MultipartBody

class ApplyOnlineFouthActivityPresenter : BasePresenter<ApplyOnlineFourthActivity>() {

    val applyOnlineFouthActivityModel : ApplyOnlineFouthActivityModel by lazy {
        ApplyOnlineFouthActivityModel()
    }

    fun requestData(phoneNum: String, email: String, address: String, job: String, havePet: Boolean,
                     regType: String, petInfo: String, adoptType: String, animalId: Int, animalImg: MultipartBody.Part?){
        applyOnlineFouthActivityModel.requestApplyOnlineFouthResponse(phoneNum, email, address, job, havePet,regType, petInfo,adoptType,animalId
        ,animalImg)
    }

    fun responseData(data : PostDogDetailHeartResponse){
        Log.v("tkm", data.toString())
        view!!.responseData(data)
    }
}