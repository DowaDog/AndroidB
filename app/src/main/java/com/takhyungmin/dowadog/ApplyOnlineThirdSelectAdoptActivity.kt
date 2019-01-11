package com.takhyungmin.dowadog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.apply.online.ApplyOnlineFourthActivity
import com.takhyungmin.dowadog.utils.CustomSingleResDialog
import kotlinx.android.synthetic.main.activity_apply_online_third_select_adopt.*
import org.jetbrains.anko.startActivity

@Suppress("UNUSED_VALUE")
class ApplyOnlineThirdSelectAdoptActivity : BaseActivity(), View.OnClickListener {


    var address : String = ""
    var job : String = ""
    var humanImgUri : String = ""

    var animalImgUri : String = ""

    var animalDescription = ""

    var animalId = 9999

    var tempPossiblePeriod = ""

    var havePet = false

    var adoptType = ""
    private var isCheckFlag = 0

    private val customDialog : CustomSingleResDialog by lazy {
        CustomSingleResDialog(ApplyOnlineThirdSelectAdoptActivity@ this, "필수 항목을 입력해주세요.",responseListener, "확인")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_online_third_select_adopt)

        init()
        getIntentItem()
    }

    override fun onClick(v: View?) {
        when (v){
            btn_back_apply_online_third_select_adopt_act -> {
                finish()
            }

            btn_check_box_apply_online_third_select_adopt_act -> {
                if (isCheckFlag == 0) {
                    iv_check_box_apply_online_third_select_adopt_act.setImageResource(R.drawable.adopt_3step_check_orange)
                    isCheckFlag = 1
                    btn_next_apply_online_third_select_adopt_act.setBackgroundColor(Color.parseColor("#ffc233"))
                }else {
                    iv_check_box_apply_online_third_select_adopt_act.setImageResource(R.drawable.adopt_3step_check_gray)
                    isCheckFlag = 0
                    btn_next_apply_online_third_select_adopt_act.setBackgroundColor(Color.parseColor("#e2e2e2"))
                }
            }

            btn_next_apply_online_third_select_adopt_act -> {
                if (isCheckFlag == 0) {
                    // 다이얼로그 불러오기
                    openDemandValidResponseDialog()
                }else {
                    // 다음으로 넘어가기
                    startActivity<ApplyOnlineFourthActivity>("address" to address, "job" to job, "humanImgUri" to humanImgUri,
                            "animalDescription" to animalDescription, "animalImgUri" to animalImgUri, "tempPossiblePeriod" to tempPossiblePeriod,
                    "id" to animalId, "havePet" to havePet, "adoptType" to adoptType)
                }
            }
        }
    }

    private fun init(){
        btn_back_apply_online_third_select_adopt_act.setOnClickListener(this)
        btn_check_box_apply_online_third_select_adopt_act.setOnClickListener(this)
        btn_next_apply_online_third_select_adopt_act.setOnClickListener(this)
        root_apply_online_third_select_adopt_act.clicks().subscribe{
            val imm: InputMethodManager = applicationContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(root_apply_online_third_select_adopt_act.windowToken, 0)
        }
    }

    private fun openDemandValidResponseDialog() {
        customDialog!!.show()
    }

    private val responseListener = View.OnClickListener { customDialog!!.dismiss() }

    fun getIntentItem(){
        address = intent.getStringExtra("address")
        job = intent.getStringExtra("job")
        humanImgUri = intent.getStringExtra("humanImgUri")
        animalImgUri= intent.getStringExtra("animalImgUri")
        animalDescription = intent.getStringExtra("animalDescription")
        animalId= intent.getIntExtra("id", 9999)
        havePet = intent.getBooleanExtra("havePet", false)
        adoptType = intent.getStringExtra("adoptType")
    }
}
