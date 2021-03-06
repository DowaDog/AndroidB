package com.takhyungmin.dowadog.presenter.fragment

import com.takhyungmin.dowadog.contents.adapter.ContentsSenseItem
import com.takhyungmin.dowadog.contents.fragment.ContentsSenseFragment
import com.takhyungmin.dowadog.contents.model.ContentsModel
import com.takhyungmin.dowadog.contents.model.get.GetEduContentsContents
import com.takhyungmin.dowadog.presenter.BasePresenter

class ContentsSenseFragmentPresenter : BasePresenter<ContentsSenseFragment>() {

    val contentsModel : ContentsModel by lazy {
        ContentsModel()
    }

    lateinit var contentsEduItems : ArrayList<ContentsSenseItem>

    //    fun toDetail(width : Int, height : Int, left : Int, top : Int, title : String, sub : String){
//        view!!.toDetail(width, height, left, top, title, sub)
//    }

    val toDetail = {id : Int, image : String, scrap : Boolean, title : String ->
        view!!.toDetail(id, image, scrap, title)
    }

    val requestData = {
        contentsModel.requestSenseList()
    }

    val responseData = {contents : ArrayList<GetEduContentsContents> ->
        view!!.responseList(contents)
    }

}