package com.takhyungmin.dowadog.adoptedanimal

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.jakewharton.rxbinding2.view.clicks
import com.takhyeongmin.dowadogbeta.modifyinfoanimal.ModifyInfoAnimalActivity
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.adoptedanimal.model.AdoptedAnimalObject
import com.takhyungmin.dowadog.adoptedanimal.model.get.GETAdoptedAnimalResponse
import com.takhyungmin.dowadog.dogdetail.DogDetailActivity
import com.takhyungmin.dowadog.presenter.activity.AdoptedAnimalActivityPresenter
import kotlinx.android.synthetic.main.activity_adopted_animal.*

class AdoptedAnimalActivity : BaseActivity(){

    private val dataList : ArrayList<AdoptedAnmalAdapterData> by lazy {
        ArrayList<AdoptedAnmalAdapterData>()
    }

    private lateinit var adoptedAnimalActivityPresenter : AdoptedAnimalActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopted_animal)

        init()
        setRVAdpater()

        initPresenter()
        adoptedAnimalActivityPresenter.adoptedRequestData()
    }

    private fun init(){
        btn_back_adopted_animal_act.clicks().subscribe{
            finish()
        }
    }


    private fun setRVAdpater(){
//        dataList.add(AdoptedAnmalAdapterData("미키", "슈나우저", 1, 1, "수컷"))
//        dataList.add(AdoptedAnmalAdapterData("탱자", "스코티", 0, 1, "암컷"))
//        dataList.add(AdoptedAnmalAdapterData("장군이", "치와와", 1, 5, "수컷"))
//        dataList.add(AdoptedAnmalAdapterData("워즈", "닥스훈트", 1, 1, "암컷"))
//       // var adoptedAnimalAdapter = AdoptedAnimalAdapter(this@AdoptedAnimalActivity, dataList)
//        rv_adoped_animal_act.adapter = adoptedAnimalAdapter
//        rv_adoped_animal_act.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
    }

    fun responseAdoptedData(data: GETAdoptedAnimalResponse) {

        data?.let {

            var adoptedAnimalAdapter = AdoptedAnimalAdapter(this, it.data )
            //여기에 받아온 데이터들을 가져와서 보여주는 것을 해야함 (함수로 만들던 여기에 구현하던)
            // Log.v("TAGG", data.toString())

            rv_adoped_animal_act.adapter = adoptedAnimalAdapter
            rv_adoped_animal_act.layoutManager = LinearLayoutManager(this)

            rv_adoped_animal_act.setNestedScrollingEnabled(false)

        }
    }

    //view에 presenter 붙여주기
    private fun initPresenter() {

        adoptedAnimalActivityPresenter= AdoptedAnimalActivityPresenter()
        // 뷰 붙여주는 작업
        adoptedAnimalActivityPresenter.view = this
        AdoptedAnimalObject.adoptedAnimalActivityPresenter = adoptedAnimalActivityPresenter

        Log.v("TAGG", "letter 엑티비티 이닛프레젠터")

    }

    fun adoptedAnimalIntent(id : Int) {

        var intent = Intent(this, ModifyInfoAnimalActivity::class.java)
        intent.putExtra("animalId", id)
        startActivity(intent)

    }
}