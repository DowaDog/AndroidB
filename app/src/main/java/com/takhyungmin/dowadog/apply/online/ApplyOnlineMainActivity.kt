package com.takhyungmin.dowadog.apply.online

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.View
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.utils.CustomTypeSpan
import kotlinx.android.synthetic.main.activity_apply_online_main.*
import org.jetbrains.anko.startActivity

class ApplyOnlineMainActivity : AppCompatActivity() {

    var animalId = 9999
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_online_main)
        animalId = intent.getIntExtra("id", 9999)
        init()
        setBinding(animalId)

    }

    fun init(){
        val font1 = Typeface.createFromAsset(assets, "nanum_square_bold.ttf")
        val font2 = Typeface.createFromAsset(assets, "nanum_square_light.ttf")

        val ssb = SpannableStringBuilder("온라인 신청서를 작성해주세요.")

        ssb.setSpan(CustomTypeSpan("", font1), 0, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        ssb.setSpan(CustomTypeSpan("", font2), 7, "온라인 신청서를 작성해주세요.".length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        tv_online_apply_main_infotext.text = ssb
//
//        val params = layout_online_apply_main_process.layoutParams
//        params.width = tv_online_apply_main_infotext.width
//        layout_online_apply_main_process.layoutParams = params
    }

    fun setBinding(animalId : Int){
        btn_apply_online_main_write.setOnClickListener {
            startActivity<ApplyOnlineFirstActivity>("id" to animalId)
        }
    }
}
