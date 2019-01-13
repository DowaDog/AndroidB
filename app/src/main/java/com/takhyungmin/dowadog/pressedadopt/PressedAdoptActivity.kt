package com.takhyungmin.dowadog.pressedadopt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.presenter.activity.AdoptProcessedActivityPresenter
import com.takhyungmin.dowadog.utils.CustomDialog
import com.takhyungmin.dowadog.utils.CustomPressedAdoptDialog
import kotlinx.android.synthetic.main.activity_pressed_adopt.*



class PressedAdoptActivity : BaseActivity() {

    var name = ""
    var num = ""
    var animaiId = 0
    lateinit var customShelterDialog : CustomPressedAdoptDialog
    private val pressedAdoptPresenter : AdoptProcessedActivityPresenter by lazy{
        AdoptProcessedActivityPresenter()
    }

//    private val customShelterDialog: CustomPressedAdoptDialog by lazy {
//        CustomPressedAdoptDialog(this@PressedAdoptActivity, name, "보호소 번호가 들어가야함", cancleBtnListener, confirmBtnListener, "취소", "확인")
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pressed_adopt)

        name = intent.getStringExtra("spotName")
        num = intent.getStringExtra("num")
        animaiId = intent.getIntExtra("id", 13)
        Log.v("check","AdoptAct num : "+ num + " spotName " + name)

        init()
    }

    private fun init(){
        PressedAdoptObject.pressedAdoptPresenter = pressedAdoptPresenter
        btn_back_pressed_adopt_act.clicks().subscribe{
            finish()
        }
        btn_adopt_pressed_adopt_act.clicks().subscribe{

            customShelterDialog = CustomPressedAdoptDialog(this, name, num, cancleBtnListener, confirmBtnListener, "취소", "확인")

            customShelterDialog.show()
        }
    }

    private var cancleBtnListener = View.OnClickListener {
        customShelterDialog.dismiss()
    }

    private var confirmBtnListener = View.OnClickListener {
        // 전화 번호

        //startActivity(Intent("android.intent.action.DIAL", Uri.parse("tel:$num")))
        requestReadExternalStoragePermission()
        customShelterDialog.dismiss()
    }

    private fun requestReadExternalStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 4009)
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num))
                startActivityForResult(intent, 4009)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 4009)
//                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num))
//                startActivityForResult(intent, 4009)
            }
        } else {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num))
            startActivityForResult(intent, 4009)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 4009) {
            //결과에 대해 허용을 눌렀는지 체크하는 조건문이구요!
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //이곳은 외부저장소 접근을 허용했을 때에 대한 로직을 쓰시면됩니다. 우리는 앨범을 여는 메소드를 호출해주면되겠죠?
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num))
                startActivityForResult(intent, 4009)
            } else {
                //이곳은 외부저장소 접근 거부를 했을때에 대한 로직을 넣어주시면 됩니다.
                finish()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 4009){
            logoutCustomDialog.show()
        }
            //startActivity(Intent(this, HomeActivity::class.java))
    }

    val logoutCustomDialog : CustomDialog  by lazy {
        CustomDialog(this, "입양 신청을 하셨나요?", responseRight, responseLeft,"취소", "확인")
    }

    private val responseRight = View.OnClickListener {
        logoutCustomDialog!!.dismiss()
        //입양 신청 no
    }
    private val responseLeft = View.OnClickListener {
        //startActivity(Intent(this, LoginActivity::class.java))
        pressedAdoptPresenter.requestAdopt(animaiId)
        logoutCustomDialog!!.dismiss()
        //입양 신청 yes
    }

}
