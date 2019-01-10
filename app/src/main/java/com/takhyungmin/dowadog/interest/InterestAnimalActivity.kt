package com.takhyungmin.dowadog.interest

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.adopt.model.get.UrgentAnimalData
import com.takhyungmin.dowadog.dogdetail.DogDetailActivity
import com.takhyungmin.dowadog.interest.adapter.InterestAnimalAdapter
import com.takhyungmin.dowadog.interest.model.InterestAnimalObject
import com.takhyungmin.dowadog.interest.model.get.GETInterestAnimalResponse
import com.takhyungmin.dowadog.mypage.model.Data
import com.takhyungmin.dowadog.presenter.activity.InterestAnimalActivityPresenter
import kotlinx.android.synthetic.main.activity_interest_animal.*

class InterestAnimalActivity : BaseActivity(), View.OnClickListener {

    private lateinit var interestAnimalActivityPresenter: InterestAnimalActivityPresenter

    private lateinit var InterestAnimalAdapter: InterestAnimalAdapter
    lateinit var interestGetData: Data
    private lateinit var requestManager: RequestManager

    override fun onClick(v: View?) {
        when (v) {
            btn_back_interest_animal_act -> {
                //back버튼 누르면
                finish()
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_animal)

        init()
        setRVAdapter()
    }

    private fun init() {
        rv_interest_ani_act.setFocusable(false)
        btn_back_interest_animal_act.setOnClickListener(this)

        initPresenter()

        interestAnimalActivityPresenter = InterestAnimalActivityPresenter()
        interestAnimalActivityPresenter.view = this

        interestAnimalActivityPresenter.initView()
        interestAnimalActivityPresenter.requestData()

    }

    private fun setRVAdapter() {

        var animalItem: ArrayList<UrgentAnimalData> = ArrayList()

//        animalItem.add(UrgentAnimalData("D-1", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "믹스견", "[충청]"))
//        animalItem.add(UrgentAnimalData("D-2", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "페르시안", "[전라도] "))
//        animalItem.add(UrgentAnimalData("D-1", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "믹스견", "[충청]"))
//        animalItem.add(UrgentAnimalData("D-2", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "페르시안", "[전라도] "))
//        animalItem.add(UrgentAnimalData("D-1", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "믹스견", "[충청]"))
//        animalItem.add(UrgentAnimalData("D-2", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "페르시안", "[전라도] "))
//        animalItem.add(UrgentAnimalData("D-1", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "믹스견", "[충청]"))
//        animalItem.add(UrgentAnimalData("D-2", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "페르시안", "[전라도] "))
//        animalItem.add(UrgentAnimalData("D-1", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "믹스견", "[충청]"))
//        animalItem.add(UrgentAnimalData("D-2", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "페르시안", "[전라도] "))
//        animalItem.add(UrgentAnimalData("D-1", "http://img.hani.co.kr/imgdb/resize/2018/0907/00502739_20180907.JPG", "믹스견", "[충청]"))

        //animalItem.add(UrgentAnimalData("D-3","", "","","[인천] 러시안 블루" ))

        //var interestAnimalAdapter = InterestAnimalAdapter(this, animalItem)

        // rv_interest_ani_act.adapter = interestAnimalAdapter
        //rv_interest_ani_act.layoutManager = GridLayoutManager(this, 2)
    }

    fun initView(contentsEduDetailItems: ArrayList<GETInterestAnimalResponse>) {
        requestManager = Glide.with(this)
        //contentsEduDetailRvAdapter = ContentsEduDetailRvAdapter(contentsEduDetailItems, requestManager)
        rv_interest_ani_act.layoutManager = LinearLayoutManager(this)
        rv_interest_ani_act.adapter = InterestAnimalAdapter
    }

    fun responseData(data: GETInterestAnimalResponse) {

        data?.let {

            requestManager = Glide.with(this)
            InterestAnimalAdapter = InterestAnimalAdapter(this, data.data, requestManager)
            rv_interest_ani_act.layoutManager = GridLayoutManager(this, 2)
            rv_interest_ani_act.adapter = InterestAnimalAdapter
        }
    }

    fun animalDetailResponseData(id: Int) {

//        var intent = Intent(this, DogDetailActivity::class.java)
//        intent.putExtra("animalId", id)
//        startActivity(intent)

        var intent = Intent(this@InterestAnimalActivity, DogDetailActivity::class.java)
        intent.putExtra("animalId", id)
        startActivityForResult(intent, 6900)


        //startActivity<DogDetailActivity>("id" to Int)
    }

    //view에 presenter 붙여주기
    private fun initPresenter() {

        interestAnimalActivityPresenter = InterestAnimalActivityPresenter()
        // 뷰 붙여주는 작업
        interestAnimalActivityPresenter.view = this
        InterestAnimalObject.interestAnimalActivityPresenter = interestAnimalActivityPresenter

        Log.v("TAGG", "Interest Animal 엑티비티 이닛프레젠터")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 6900) {
            interestAnimalActivityPresenter.requestData()
        }
    }

}
