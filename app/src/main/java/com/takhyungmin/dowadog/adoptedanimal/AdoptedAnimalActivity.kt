package com.takhyungmin.dowadog.adoptedanimal

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import kotlinx.android.synthetic.main.activity_adopted_animal.*

class AdoptedAnimalActivity : BaseActivity(){

    private val dataList : ArrayList<AdoptedAnmalAdapterData> by lazy {
        ArrayList<AdoptedAnmalAdapterData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopted_animal)

        init()
        setRVAdpater()

    }

    private fun init(){
        btn_back_adopted_animal_act.clicks().subscribe{

        }
    }


    private fun setRVAdpater(){
        dataList.add(AdoptedAnmalAdapterData("미키", "슈나우저", 1, 1, "수컷"))
        dataList.add(AdoptedAnmalAdapterData("탱자", "스코티", 0, 1, "암컷"))
        dataList.add(AdoptedAnmalAdapterData("장군이", "치와와", 1, 5, "수컷"))
        dataList.add(AdoptedAnmalAdapterData("워즈", "닥스훈트", 1, 1, "암컷"))
        var adoptedAnimalAdapter = AdoptedAnimalAdapter(this@AdoptedAnimalActivity, dataList)
        rv_adoped_animal_act.adapter = adoptedAnimalAdapter
        rv_adoped_animal_act.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
    }

}