package com.takhyungmin.dowadog.search

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.SearchEditTextActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.sdk25.coroutines.textChangedListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchActivity : BaseActivity(), View.OnClickListener {



    override fun onClick(v: View?) {
        when (v!!) {
            // 검색 버튼
            btn_search_search_act -> {
                if(et_keyword_search_act.text.length <= 0){
                    toast("키워드를 입력해주세요")
                }else {
                    startActivity<SearchEditTextActivity>("keyword" to et_keyword_search_act.text.toString())
                }
            }
            btn_back_search_act -> {
                finish()
            }
            et_keyword_search_act -> {
                rl_recommend_box_search_act.visibility = View.VISIBLE
            }
            rv_search_act -> {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(rv_search_act.windowToken, 0)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        init()

        setSearchBtnTextChangeListener()

        var recommendKeyword: ArrayList<String> = ArrayList()
        recommendKeyword.add("대형견")
        recommendKeyword.add("사설보호소")
        recommendKeyword.add("동물병원")
        setFirstRVAdapter(recommendKeyword)
        var recommendKeyword2: ArrayList<String> = ArrayList()
        recommendKeyword2.add("수도권지역")
        recommendKeyword2.add("소형견")
        recommendKeyword2.add("공공보호소")

        setSecondRVAdapter(recommendKeyword2)
        var recommendKeyword3: ArrayList<String> = ArrayList()
        recommendKeyword3.add("케어입양센터")
        recommendKeyword3.add("서울지역")
        recommendKeyword3.add("중형견")
        setThirdRVAdapter(recommendKeyword3)
    }

    private fun init() {
        btn_search_search_act.setOnClickListener(this)
        btn_back_search_act.setOnClickListener(this)
        et_keyword_search_act.setOnClickListener(this)
        rv_search_act.setOnClickListener(this)
        setOnEnterListener()
    }


    fun setSearchBtnTextChangeListener() {
        et_keyword_search_act.textChangedListener {
            afterTextChanged {
                if (et_keyword_search_act.text.toString().length > 0) {
                    btn_search_search_act.setBackgroundColor(Color.parseColor("#ffc233"))
                } else {
                    btn_search_search_act.setBackgroundColor(Color.parseColor("#C5C5C5"))
                }
            }
        }
        //et_keyword_search_act.setOn
    }

    fun setFirstRVAdapter(recommendKeyword: ArrayList<String>) {
        var recyclerViewAdapter = SearchRecyclerViewAdapter(this@SearchActivity, recommendKeyword)
        rv_one_recommend_search_act.adapter = recyclerViewAdapter
        rv_one_recommend_search_act.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayout.HORIZONTAL, false)
    }

    fun setSecondRVAdapter(recommendKeyword: ArrayList<String>){
        var recyclerViewAdapter = SearchRecyclerViewAdapter(this@SearchActivity, recommendKeyword)
        rv_two_recommend_search_act.adapter = recyclerViewAdapter
        rv_two_recommend_search_act.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayout.HORIZONTAL, false)
    }

    fun setThirdRVAdapter(recommendKeyword: ArrayList<String>){
        var recyclerViewAdapter = SearchRecyclerViewAdapter(this@SearchActivity, recommendKeyword)
        rv_three_recommend_search_act.adapter = recyclerViewAdapter
        rv_three_recommend_search_act.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayout.HORIZONTAL, false)
    }

    fun setPastSearchKeywordRVAdapter(pastKeyword: ArrayList<String>){
        var searchPastKeywordRVAdapter = SearchPastKeywordRVAdapter(this@SearchActivity, pastKeyword)
        rv_past_keyword_search_act.adapter = searchPastKeywordRVAdapter
        rv_past_keyword_search_act.layoutManager = LinearLayoutManager(this@SearchActivity)
    }

    //startActivityForResult를 통해 실행한 엑티비티에 대한 callback을 처리하는 메소드입니다!
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //SearchKeywordResultActivity를 다녀왔는지 분기
        if (requestCode == 9898) {
            var recommendKeyword: ArrayList<String> = ArrayList()
            recommendKeyword.add("대형견")
            recommendKeyword.add("사설보호소")
            recommendKeyword.add("동물병원")
            setFirstRVAdapter(recommendKeyword)
            var recommendKeyword2: ArrayList<String> = ArrayList()
            recommendKeyword2.add("수도권")
            recommendKeyword2.add("소형견")
            recommendKeyword2.add("공공보호소")

            setSecondRVAdapter(recommendKeyword2)
            var recommendKeyword3: ArrayList<String> = ArrayList()
            recommendKeyword3.add("케어입양센터")
            recommendKeyword3.add("서울")
            recommendKeyword3.add("중형견")
            setThirdRVAdapter(recommendKeyword3)
        }
    }

    fun setOnEnterListener() {
        et_keyword_search_act.setOnKeyListener { v, keyCode, event ->
            if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                btn_search_search_act.performClick()
                true
            }
            false
        }
    }
}

