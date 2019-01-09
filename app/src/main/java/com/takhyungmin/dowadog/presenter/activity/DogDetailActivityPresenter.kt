package com.takhyungmin.dowadog.presenter.activity

import com.takhyungmin.dowadog.dogdetail.DogDetailActivity
import com.takhyungmin.dowadog.dogdetail.model.DogDetailModel
import com.takhyungmin.dowadog.dogdetail.model.get.GetDogDetailResponse
import com.takhyungmin.dowadog.presenter.BasePresenter

class DogDetailActivityPresenter : BasePresenter<DogDetailActivity>() {

    private val dogDetailModel: DogDetailModel by lazy {
        DogDetailModel()
    }


    fun requestData(animalId: Int){
        dogDetailModel.getDogDetailData(animalId)
    }

    fun responseData(data : GetDogDetailResponse){
        view!!.responseData(data)
    }

    fun requestHeartData(animalId: Int){
        dogDetailModel.postDogDetailHeart(animalId)
    }

}