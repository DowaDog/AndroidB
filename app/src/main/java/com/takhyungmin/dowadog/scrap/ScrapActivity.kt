package com.takhyungmin.dowadog.scrap

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.contents.activity.ContentsEduDetailActivity
import com.takhyungmin.dowadog.contents.activity.ContentsSenseDetailActivity
import com.takhyungmin.dowadog.presenter.activity.ScrapActivityPresenter
import com.takhyungmin.dowadog.scrap.scrapmodel.ScrapObject
import com.takhyungmin.dowadog.scrap.scrapmodel.get.GetMyScrapData
import com.takhyungmin.dowadog.scrap.scrapmodel.get.GetMyScrapResponse
import kotlinx.android.synthetic.main.activity_apply_online_first.*
import kotlinx.android.synthetic.main.activity_scrap.*

class ScrapActivity : BaseActivity(), View.OnClickListener {

    lateinit var myScrapList: GetMyScrapResponse

    lateinit var scrapActivityPresenter: ScrapActivityPresenter


    override fun onClick(v: View?) {
        when (v) {
            btn_back_scrap_act -> {
                finish()
            }
        }
    }

    lateinit var scrapRecyclerViewAdapter: ScrapRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrap)
        initPresenter()
        scrapActivityPresenter.requestMyScrapList()
        init()

        initView()
    }

    private fun init() {
        btn_back_scrap_act.setOnClickListener(this)
    }

    private fun initView() {
    }

    private fun setRVListener(data : ArrayList<GetMyScrapData>) {
//        dataList = ArrayList()
//        dataList.add(ScrapRVData(1, "작성자 이름", "2018.12.12"))
//        dataList.add(ScrapRVData(1, "작성자 이름", "2018.12.12"))
//        dataList.add(ScrapRVData(1, "작성자 이름", "2018.12.12"))
//        dataList.add(ScrapRVData(1, "작성자 이름", "2018.12.12"))
//        dataList.add(ScrapRVData(1, "작성자 이름", "2018.12.12"))
//        dataList.add(ScrapRVData(1, "작성자 이름", "2018.12.12"))
//        dataList.add(ScrapRVData(1, "작성자 이름", "2018.12.12"))
        scrapRecyclerViewAdapter = ScrapRecyclerViewAdapter(this@ScrapActivity, data)
        rv_scrap_act.adapter = scrapRecyclerViewAdapter
        rv_scrap_act.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
    }

    private fun initPresenter() {
        scrapActivityPresenter = ScrapActivityPresenter()
        // 뷰 붙여주는 작엄
        scrapActivityPresenter.view = this
        ScrapObject.scrapActivityPresenter = scrapActivityPresenter
    }

    fun responseScrapData(data: GetMyScrapResponse) {
        myScrapList = data
        myScrapList?.data?.let {
            setRVListener(it)
        }

    }

    fun scrapEducationDetail(id: Int, type : String) {

//        var intent = Intent(this, DogDetailActivity::class.java)
//        intent.putExtra("animalId", id)
//        startActivity(intent)

        if(type=="education"){
            var intent = Intent(this@ScrapActivity, ContentsEduDetailActivity::class.java)
            intent.putExtra("id", id)
            startActivityForResult(intent, 6001)
        }else{
            var intent = Intent(this@ScrapActivity, ContentsSenseDetailActivity::class.java)
            intent.putExtra("id", id)
            startActivityForResult(intent, 6001)
        }



        //startActivity<DogDetailActivity>("id" to Int)
    }

    //startActivityForResult를 통해 실행한 엑티비티에 대한 callback을 처리하는 메소드입니다!
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //REQUEST_CODE_SELECT_IMAGE를 통해 앨범에서 보낸 요청에 대한 Callback인지를 체크!!!
        if (requestCode == 6001) {
            scrapActivityPresenter.requestMyScrapList()
        }

        // 마이페이지 수정 다녀온 이후
        if(requestCode == 3003){
            Log.v("TAGG", "마이페이지 다녀옴")
            init()
        }
    }
}
