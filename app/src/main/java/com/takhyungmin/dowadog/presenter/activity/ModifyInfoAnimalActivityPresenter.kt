package com.takhyungmin.dowadog.presenter.activity

import android.util.Log
import com.takhyeongmin.dowadogbeta.modifyinfoanimal.ModifyInfoAnimalActivity
import com.takhyungmin.dowadog.modifyinfoanimal.model.ModifyInfoAnimalModel
import com.takhyungmin.dowadog.modifyinfoanimal.model.get.GETModifyInfoAnimalResponse
import com.takhyungmin.dowadog.modifyinfoanimal.model.put.PUTModifyInfoAnimalResponse
import com.takhyungmin.dowadog.presenter.BasePresenter
import okhttp3.MultipartBody

class ModifyInfoAnimalActivityPresenter : BasePresenter<ModifyInfoAnimalActivity>() {

    private val modifyInfoAnimalModel : ModifyInfoAnimalModel by lazy {
        ModifyInfoAnimalModel()
    }

    //모델에게 일을 시킴
    fun GETModifyrequestData(id : Int){
        Log.v("TAGG", "modifyInfoAnimal 프레젠터 리퀘스트데이터")
        modifyInfoAnimalModel.getModifyData(id)
    }
    //view에게 데이터 전달
    fun GETresponseData(data : GETModifyInfoAnimalResponse){
        Log.v("TAGG", "modifyInfoAnimal 프레젠터 리스폰스데이터")
        view!!.responseData(data)
    }

    fun PUTModifyrequestData(animalid : Int, name : String, kind : String, weight : String, neuterYn : Boolean,
                             age : String, img : MultipartBody.Part?, gender : String, inoculation : ArrayList<String>) {
        Log.v("TAGG", "modifyInfoAnimal PUT 프레젠터 리퀘스트데이터")
        Log.v("데이터", name + " " + gender + " " + kind + " " + weight + " " + neuterYn + " " +
                " " + age + " ")

        modifyInfoAnimalModel.putAnimalData(animalid, name, gender, kind, weight, neuterYn, age, img, inoculation)
    }
    fun PUTresponseData(data : PUTModifyInfoAnimalResponse){
        Log.v("TAGG", "modifyInfoAnimal PUT 프레젠터 리스폰스데이터")
        view!!.PutResponseData(data)
    }

}