package com.takhyungmin.dowadog.searchkeywordresult

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.dogdetail.DogDetailActivity
import com.takhyungmin.dowadog.presenter.activity.SearchKeywordResultActivityPresenter
import com.takhyungmin.dowadog.searchresult.SearchResultObject
import com.takhyungmin.dowadog.searchresult.adapter.SearchResultAdapter
import com.takhyungmin.dowadog.searchresult.model.ggg.Content
import com.takhyungmin.dowadog.searchresult.model.ggg.ccc
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchKeywordResultActivity : BaseActivity(), View.OnClickListener {

    var isLoading = false

    var currentPage = 0

    var TOTAL_PAGE = 10

    var isLast = false

    lateinit var tag: String

    lateinit var searchFilterResultResponse: ccc

    private lateinit var searchKeywordResultActivityPresenter: SearchKeywordResultActivityPresenter

    lateinit var searchResultAdapter: SearchResultAdapter

    lateinit var requestManager: RequestManager

    override fun onClick(v: View?) {
        when (v) {
            btn_back_search_result_act -> {
                finish()
            }
        }
    }

//    private fun getIntentData(){
//        keyword = intent.getStringExtra("keyword")
//        Log.v("SearchResultActivity", keyword)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // getIntentData()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        tag = intent.getStringExtra("keyword")
        init()
        initPresenter()
        searchKeywordResultActivityPresenter.requestTagData(tag)
    }

    private fun init() {
        tv_keyword_search_act.text = "\'" + tag + "\' " + "태그 " + "검색결과"
        tv_temp_search_act.text = ""
        btn_back_search_result_act.setOnClickListener(this)
        progress_search_result.visibility = View.GONE
//        nested_scroll_search_result_act.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
//            Log.v("scroll", "scroll")
//            if (scrollY == (v.getChildAt(0).height - v.height)) {
//                //scroll in bottom
//                Log.v("scroll", "bottom")
//                if (!isLoading and !isLast) {
//                    //isLast = 스크롤의 마지막이 아니라 전체 아이템 중 마지
//                    isLoading = true
//                    Log.v("scroll", currentPage.toString())
//                    currentPage++
//                    Handler().postDelayed(Runnable {
//                        //communityFragmentPresenter.nextPage(currentPage, TOTAL_PAGE)
//                        Log.v("scroll", "more")
//                        searchKeywordResultActivityPresenter.requestTagData(tag)
//                    }, 800)
//                }
//            }
//        })
    }

    private fun setRVAdapter(dataList: ArrayList<Content>) {
        //animalItem.add(UrgentAnimalData("D-3","", "","","[인천] 러시안 블루" ))

        requestManager = Glide.with(this)
        searchResultAdapter = SearchResultAdapter(this, dataList, requestManager, 1)
        rv_search_result_act.adapter = searchResultAdapter
        rv_search_result_act.layoutManager = GridLayoutManager(this, 2)
    }

//    fun requestResultData(page: Int, limit: Int, keyword: String) {
//        searchKeywordResultActivityPresenter.responseData(page, limit, keyword)
//    }
//
//    fun responseData(data: ccc) {
//        data?.let {
//            searchFilterResultResponse = data
//            Log.v("TAGGG", searchFilterResultResponse.toString())
//            setRVAdapter(searchFilterResultResponse.data.content)
//            if(data.data.content.size<= 0){
//                rl_no_search_result_act.visibility = View.VISIBLE
//                nested_scroll_search_result_act.visibility = View.GONE
//            }else {
//                rl_no_search_result_act.visibility = View.GONE
//                nested_scroll_search_result_act.visibility = View.VISIBLE
//            }
//        }
//    }


    private fun initPresenter() {
        searchKeywordResultActivityPresenter = SearchKeywordResultActivityPresenter()
        // 뷰 붙여주는 작엄
        searchKeywordResultActivityPresenter.view = this
        SearchResultObject.searchKeywordResultActivityPresenter = searchKeywordResultActivityPresenter
    }

//    fun loadNextPage(results: ArrayList<Content>) {
//        Log.v("scroll", "add")
//        searchResultAdapter.addAll(results)
//        //currentPage += 1
//        isLoading = false
//        if (TOTAL_PAGE > results.size){
//          isLast = true
//
//        }
//
//    }

    fun responseTagData(data: ccc) {
        data?.let {
            searchFilterResultResponse = data
            Log.v("TAGGG", searchFilterResultResponse.toString())
            setRVAdapter(searchFilterResultResponse.data.content)
            if (data.data.content.size <= 0) {
                rl_no_search_result_act.visibility = View.VISIBLE
                nested_scroll_search_result_act.visibility = View.GONE
            } else {
                rl_no_search_result_act.visibility = View.GONE
                nested_scroll_search_result_act.visibility = View.VISIBLE
            }
        }
    }

    fun toDetail(id : Int){
        var intent = Intent(this,  DogDetailActivity::class.java)
        intent.putExtra("animalId", id)
        startActivityForResult(intent, 9009)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 6900){
            Log.v("check", "다녀옴")
            searchKeywordResultActivityPresenter.requestTagData(tag)
        }

        if(requestCode == 9009){
            searchKeywordResultActivityPresenter.requestTagData(tag)
        }

    }
}