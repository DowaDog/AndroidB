package com.takhyungmin.dowadog.presenter.activity

import com.takhyungmin.dowadog.SearchEditTextActivity
import com.takhyungmin.dowadog.presenter.BasePresenter
import com.takhyungmin.dowadog.searchresult.model.SearchResultModel
import com.takhyungmin.dowadog.searchresult.model.ggg.Content
import com.takhyungmin.dowadog.searchresult.model.ggg.ccc

class SearchEditTextActivityPresenter : BasePresenter<SearchEditTextActivity>() {


    val searchResultModel : SearchResultModel by lazy{
        SearchResultModel()
    }

    fun init(){

    }

//    fun responseData(page: Int, limit: Int, searchKeyword: String){
//        searchResultModel.getKeywordSearchResulData("", "", 300, page, limit, searchKeyword)
//    }

//    fun requestData(data : ccc){
//        view!!.responseData(data)
//    }

//    fun responseMoreSearchResultList(results : ArrayList<Content>){
//        view!!.loadNextPage(results)
//    }

    fun responseData(page: Int, limit: Int, searchKeyword: String){
        searchResultModel.getSearchEditTextResulData("", "", 300, page, limit, searchKeyword)
    }

    fun requestData(data : ccc){
        view!!.responseData(data)
    }

    fun responseMoreSearchResultList(results : ArrayList<Content>){
        view!!.loadNextPage(results)
    }

    fun toDetail(id : Int){
        view!!.toDetail(id)
    }
}