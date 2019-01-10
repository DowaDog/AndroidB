package com.takhyungmin.dowadog.presenter.activity

import android.util.Log
import com.takhyungmin.dowadog.adoptedanimal.AdoptedAnimalActivity
import com.takhyungmin.dowadog.adoptedanimal.model.get.AdoptedAnimalModel
import com.takhyungmin.dowadog.adoptedanimal.model.get.GETAdoptedAnimalResponse
import com.takhyungmin.dowadog.mypage.model.get.GETMypageResponse
import com.takhyungmin.dowadog.presenter.BasePresenter

class AdoptedAnimalActivityPresenter : BasePresenter<AdoptedAnimalActivity>() {

    val adoptedAnimalModel : AdoptedAnimalModel  by lazy{
        AdoptedAnimalModel()
    }

    //모델에게 일을 시킴
    fun adoptedRequestData(){
        Log.v("TAGG", "프레젠터 리퀘스트데이터")
        adoptedAnimalModel.getAdoptedAnimalList()
    }
    //view에게 데이터 전달
    fun adoptedResponseData(data : GETAdoptedAnimalResponse){
        Log.v("TAGG", "프레젠터 리스폰스데이터")
        view!!.responseAdoptedData(data)
    }

    fun adoptedDetailResponseData(id : Int){
        view!!.adoptedAnimalIntent(id)

    }
}