package com.takhyungmin.dowadog.searchresult.model

import android.util.Log
import com.takhyungmin.dowadog.searchresult.SearchResultObject
import com.takhyungmin.dowadog.searchresult.model.ggg.ccc
import com.takhyungmin.dowadog.utils.ApplicationData
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchResultModel {
    private var searchResultNetwork: SearchResultNetwork

    init {
        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(ApplicationData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        searchResultNetwork = retrofit.create(SearchResultNetwork::class.java)
    }

    fun getSearchResulData(type: String, region: String, remainNoticeDate: Int, page: Int, limit: Int, searchKeyword: String) {

        searchResultNetwork.getSearchResultFilterResponse(ApplicationData.auth,
                type, region, remainNoticeDate, page, limit, searchKeyword).enqueue(object : Callback<ccc> {
            override fun onFailure(call: Call<ccc>?, t: Throwable?) {
                Log.e("SearchResultData 통신 실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<ccc>?, response: Response<ccc>?) {

                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            when (page) {
                                0 -> {
                                    SearchResultObject.searchResultActivityPresenter.requestData(it)
                                }
                                else -> {
                                    SearchResultObject.searchResultActivityPresenter.responseMoreSearchResultList(it.data.content)
                                }
                            }

                            // 서버에서 보내기 직전 데이터 보기
                            // 데이터 자료형이 틀린지 확인하기
                        }
            }
        })
    }

    fun getSearchEditTextResulData(type: String, region: String, remainNoticeDate: Int, page: Int, limit: Int, searchKeyword: String) {
 Log.v("TAGGG2", type + " /// " + region + " /// " + remainNoticeDate + " /// " + page + " /// " + limit + " /// " + searchKeyword)
        searchResultNetwork.getSearchResultFilterResponse(ApplicationData.auth,
                type, region, remainNoticeDate, page, limit, searchKeyword).enqueue(object : Callback<ccc> {
            override fun onFailure(call: Call<ccc>?, t: Throwable?) {
                Log.e("SearchResultData 통신 실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }

            }

            override fun onResponse(call: Call<ccc>?, response: Response<ccc>?) {

                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            when (page) {
                                0 -> {
                                    SearchResultObject.searchEditTextActivityPresenter.requestData(it)
                                }
                                else -> {
                                    SearchResultObject.searchEditTextActivityPresenter.responseMoreSearchResultList(it.data.content)
                                }
                            }

                            // 서버에서 보내기 직전 데이터 보기
                            // 데이터 자료형이 틀린지 확인하기
                        }
            }
        })
    }

//    // 요녀석
//    fun getKeywordSearchResulData(type: String, region: String, remainNoticeDate: Int, page: Int, limit: Int, searchKeyword: String) {
//
//        searchResultNetwork.getSearchResultFilterResponse(ApplicationData.auth,
//                type, region, remainNoticeDate, page, limit, searchKeyword).enqueue(object : Callback<ccc> {
//            override fun onFailure(call: Call<ccc>?, t: Throwable?) {
//                Log.e("SearchResultData 통신 실패", t.toString())
//            }
//
//            override fun onResponse(call: Call<ccc>?, response: Response<ccc>?) {
//
//                response?.takeIf { it.isSuccessful }
//                        ?.body()
//                        ?.let {
//                            when (page) {
//                                0 -> {
//                                    SearchResultObject.searchEditTextActivityPresenter.requestData(it)
//                                }
//                                else -> {
//                                    SearchResultObject.searchEditTextActivityPresenter.responseMoreSearchResultList(it.data.content)
//                                }
//                            }
//
//                            // 서버에서 보내기 직전 데이터 보기
//                            // 데이터 자료형이 틀린지 확인하기
//                        }
//            }
//        })
//    }

    fun getSearchTagResultResponse(tag: String) {

        searchResultNetwork.getSearchTagResultResponse(ApplicationData.auth, tag, 300).enqueue(object : Callback<ccc> {
            override fun onFailure(call: Call<ccc>?, t: Throwable?) {
                Log.e("getSearchTagResultRes실패", t.toString())
                if (t.toString().contains("Failed to connect to")) {
                    ApplicationData.applicationContext.toast("점검 중입니다.")
                }

                if (t.toString().contains("Unable to resolve host")) {
                    ApplicationData.applicationContext.toast("인터넷 연결 상태를 확인해주세요.")
                }
            }

            override fun onResponse(call: Call<ccc>?, response: Response<ccc>?) {
                Log.v("TAGG", tag)
                Log.v("TAGG", response.toString())
                Log.v("TAGG", response!!.body().toString())
                response?.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            SearchResultObject.searchKeywordResultActivityPresenter.responseTagData(it)
                        }
            }
        })
    }
}