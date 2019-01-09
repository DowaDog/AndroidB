package com.takhyungmin.dowadog.presenter.fragment

import android.support.v4.app.Fragment
import com.takhyungmin.dowadog.home.fragment.HomeFragment
import com.takhyungmin.dowadog.home.model.HomeModel
import com.takhyungmin.dowadog.presenter.BasePresenter

class HomeFragmentPresenter : BasePresenter<HomeFragment>() {

    val homeModel : HomeModel by lazy{
        HomeModel()
    }

    fun init(){
        //view!!.init()
        homeModel.getFragmentState()
    }

    fun changeIndicator(current : Fragment){
        view!!.replaceFragment(current)
    }

    fun swipeEnable(position : Int){
        view!!.swipeEnable(position)
    }

    val postHomeRead = {homeModel.postHomeRead()}

    val changeCheck = {check : Boolean ->
        view!!.checkChange(check)
    }
}