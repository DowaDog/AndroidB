package com.takhyungmin.dowadog

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.takhyungmin.dowadog.presenter.activity.SearchEditTextActivityPresenter
import com.takhyungmin.dowadog.searchresult.SearchResultObject
import com.takhyungmin.dowadog.searchresult.adapter.SearchResultAdapter
import com.takhyungmin.dowadog.searchresult.model.ggg.Content
import com.takhyungmin.dowadog.searchresult.model.ggg.ccc
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchEditTextActivity : BaseActivity(), View.OnClickListener {

    var isLoading = false

    var currentPage = 0

    var TOTAL_PAGE = 10

    val pagingCount = 16

    var isLast = false

    lateinit var keyword: String

    lateinit var searchFilterResultResponse: ccc

    private lateinit var searchEditTextActivityPresenter: SearchEditTextActivityPresenter

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
        keyword = intent.getStringExtra("keyword")
        initView()
        initPresenter()



        requestResultData(0, 10, keyword)
    }

    fun initView(){
        tv_keyword_search_act.text = "\'" + keyword + "\' " + "검색결과"
        tv_temp_search_act.text = ""
        btn_back_search_result_act.setOnClickListener(this)
    }

    private fun init(dataList: ArrayList<Content>) {


        if(pagingCount > dataList.size){
            isLast = true
            progress_search_result.visibility = View.GONE
        }


        nested_scroll_search_result_act.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            Log.v("scroll", "scroll")
            if (scrollY == (v.getChildAt(0).height - v.height)) {
                //scroll in bottom
                Log.v("scroll", "bottom")
                if (!isLoading and !isLast) {
                    //isLast = 스크롤의 마지막이 아니라 전체 아이템 중 마지
                    isLoading = true
                    Log.v("scroll", currentPage.toString())
                    currentPage++
                    Handler().postDelayed(Runnable {
                        //communityFragmentPresenter.nextPage(currentPage, itemCount)
                        Log.v("scroll", "more")
                        searchEditTextActivityPresenter.responseData(currentPage, pagingCount, keyword)
                    }, 800)
                }
            }

        })
    }

    private fun setRVAdapter(dataList: ArrayList<Content>) {
        //animalItem.add(UrgentAnimalData("D-3","", "","","[인천] 러시안 블루" ))

        requestManager = Glide.with(this)
        searchResultAdapter = SearchResultAdapter(this, dataList, requestManager)
        rv_search_result_act.adapter = searchResultAdapter
        rv_search_result_act.layoutManager = GridLayoutManager(this, 2)
    }

    fun requestResultData(page: Int, limit: Int, keyword: String) {
        searchEditTextActivityPresenter.responseData(page, limit, keyword)
    }

    fun responseData(data: ccc) {
        data?.let {
            searchFilterResultResponse = data
            Log.v("TAGGG", searchFilterResultResponse.toString())
            setRVAdapter(searchFilterResultResponse.data.content)
            if (data.data.content.size <= 0) {
                rl_no_search_result_act.visibility = View.VISIBLE
                nested_scroll_search_result_act.visibility = View.GONE
                init(data.data.content)
            } else {
                rl_no_search_result_act.visibility = View.GONE
                nested_scroll_search_result_act.visibility = View.VISIBLE
                init(data.data.content)
            }
        }
    }

    private fun initPresenter() {
        searchEditTextActivityPresenter = SearchEditTextActivityPresenter()
        // 뷰 붙여주는 작엄
        searchEditTextActivityPresenter.view = this
        SearchResultObject.searchEditTextActivityPresenter = searchEditTextActivityPresenter
    }

//    fun loadNextPage(results: ArrayList<Content>) {
//        Log.v("scroll", "add")
//        searchResultAdapter.addAll(results)
//        //currentPage += 1
//        isLoading = false
//        if (currentPage >= TOTAL_PAGE)
//            isLast = true
//    }

    fun loadNextPage(results : ArrayList<Content>){

        Log.v("TAGGG234", 0.toString())
        searchResultAdapter.addAll(results)
        Log.v("TAGGG234", 1.toString())
        isLoading = false
        if (pagingCount > results.size) {
            Log.v("TAGGG234", 2.toString())
            progress_search_result.visibility = View.GONE
            isLast = true
        }
    }



}