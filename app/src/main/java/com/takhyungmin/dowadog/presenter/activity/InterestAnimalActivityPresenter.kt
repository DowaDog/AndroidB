package com.takhyungmin.dowadog.presenter.activity

import android.util.Log
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.interest.InterestAnimalActivity
import com.takhyungmin.dowadog.interest.model.get.GETInterestAnimalResponse
import com.takhyungmin.dowadog.interest.model.get.InterestAnimalModel
import com.takhyungmin.dowadog.presenter.BasePresenter

class InterestAnimalActivityPresenter : BasePresenter<InterestAnimalActivity>() {

    private val interestAnimalModel : InterestAnimalModel  by lazy {
        InterestAnimalModel()
    }

    fun initView() {

    }

    //모델에게 일을 시킴
    fun requestData(){
        Log.v("TAGG", "Interest Animal 프레젠터 리퀘스트데이터")
        interestAnimalModel.getInterestAnimal()
    }
    //view에게 데이터 전달
    fun responseData(data : GETInterestAnimalResponse){
        Log.v("TAGG", "Interest Animal 프레젠터 리스폰스데이터")
        view!!.responseData(data)
    }

    fun animalDetailresponseData(id : Int){
        view!!.animalDetailResponseData(id)

    }

}