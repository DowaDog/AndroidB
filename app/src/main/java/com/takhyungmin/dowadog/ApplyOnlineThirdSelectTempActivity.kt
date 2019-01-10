package com.takhyungmin.dowadog

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.takhyungmin.dowadog.apply.online.ApplyOnlineFourthActivity
import com.takhyungmin.dowadog.utils.CustomSingleResDialog
import kotlinx.android.synthetic.main.activity_apply_online_third_select_temp.*
import org.jetbrains.anko.startActivity

class ApplyOnlineThirdSelectTempActivity : BaseActivity(), View.OnClickListener {

    var address : String = ""
    var job : String = ""
    var humanImgUri : String = ""

    var animalImgUri : String = ""

    var animalDescription = ""

    var animalId = 9999

    var havePet = false

    var tempPossiblePeriod = ""

    var adoptType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_online_third_select_temp)

        init()
        getIntentItem()
        setEditTextChangeListener()
    }

    private var isCheckFlag = 0
    private var isEditTextFlag = 0

    private val customDialog: CustomSingleResDialog by lazy {
        CustomSingleResDialog(ApplyOnlineThirdSelectTempActivity@ this, "필수 항목을 입력해주세요.", responseListener, "확인")
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_back_apply_online_third_select_temp_act -> {
                finish()
            }

            btn_check_box_apply_online_third_select_temp_act -> {
                if (isCheckFlag == 0) {
                    iv_check_box_apply_online_third_select_temp_act.setImageResource(R.drawable.adopt_3step_check_orange)
                    isCheckFlag = 1
                    if (isEditTextFlag == 1) {
                        btn_next_apply_online_third_select_temp_act.setBackgroundColor(Color.parseColor("#ffc233"))
                    } else {
                        btn_next_apply_online_third_select_temp_act.setBackgroundColor(Color.parseColor("#e2e2e2"))
                    }
                } else {
                    iv_check_box_apply_online_third_select_temp_act.setImageResource(R.drawable.adopt_3step_check_gray)
                    btn_next_apply_online_third_select_temp_act.setBackgroundColor(Color.parseColor("#e2e2e2"))
                    isCheckFlag = 0
                }
            }

            btn_next_apply_online_third_select_temp_act -> {
                if (isCheckFlag == 0) {
                    // 다이얼로그 불러오기
                    openDemandValidResponseDialog()
                } else {
                    // 다음으로 넘어가기
                    if(et_apply_online_third_select_temp_act.text.toString().length != 0){
                        tempPossiblePeriod = et_apply_online_third_select_temp_act.text.toString()
                    }
                    startActivity<ApplyOnlineFourthActivity>("address" to address, "job" to job, "humanImgUri" to humanImgUri,
                            "animalDescription" to animalDescription, "animalImgUri" to animalImgUri, "tempPossiblePeriod" to tempPossiblePeriod
                    , "id" to animalId, "havePet" to havePet, "adoptType" to adoptType)
                }
            }

            btn_et_apply_online_third_select_temp_act -> {
                et_apply_online_third_select_temp_act.performClick()
            }
        }
    }

    private fun init() {
        btn_back_apply_online_third_select_temp_act.setOnClickListener(this)
        btn_check_box_apply_online_third_select_temp_act.setOnClickListener(this)
        btn_next_apply_online_third_select_temp_act.setOnClickListener(this)
    }

    private fun openDemandValidResponseDialog() {
        customDialog!!.show()
    }

    private val responseListener = View.OnClickListener { customDialog!!.dismiss() }

    private fun setEditTextChangeListener() {

        et_apply_online_third_select_temp_act.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_apply_online_third_select_temp_act.text.toString().length <= 0 || et_apply_online_third_select_temp_act.text.toString() == null) {
                    Log.v("TAG", "맛이갔")
                    isEditTextFlag = 0
                    btn_next_apply_online_third_select_temp_act.setBackgroundColor(Color.parseColor("#e2e2e2"))
                } else {
                    isEditTextFlag = 1
                    Log.v("TAG", "맛이갔2")
                    if (isCheckFlag == 1) {
                        btn_next_apply_online_third_select_temp_act.setBackgroundColor(Color.parseColor("#ffc233"))
                    } else {
                        btn_next_apply_online_third_select_temp_act.setBackgroundColor(Color.parseColor("#e2e2e2"))
                    }
                }
            }
        })
    }

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