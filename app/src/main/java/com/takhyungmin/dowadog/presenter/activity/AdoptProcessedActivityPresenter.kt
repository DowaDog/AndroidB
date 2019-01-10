package com.takhyungmin.dowadog.presenter.activity

import com.takhyungmin.dowadog.presenter.BasePresenter
import com.takhyungmin.dowadog.pressedadopt.PressedAdoptActivity
import com.takhyungmin.dowadog.pressedadopt.model.PressedAdoptModel

class AdoptProcessedActivityPresenter : BasePresenter<PressedAdoptActivity>() {

    val pressedAdoptModel : PressedAdoptModel by lazy{
        PressedAdoptModel()
    }

    fun requestAdopt(animalId : Int){
        pressedAdoptModel.postDirectAdoptRequest(animalId)
    }
}