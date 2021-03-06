package com.takhyungmin.dowadog.apply.online

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.mypage.MypageSettingActivity
import com.takhyungmin.dowadog.presenter.activity.ApplyOnlineFristActiviyPresenter
import com.takhyungmin.dowadog.utils.ApplicationData
import com.takhyungmin.dowadog.utils.CustomSingleResDialog
import kotlinx.android.synthetic.main.activity_apply_online_first.*
import org.jetbrains.anko.startActivity

class ApplyOnlineFirstActivity : AppCompatActivity() {

    private lateinit var applyOnlineFirstActiviyPresenter: ApplyOnlineFristActiviyPresenter
    private var agreeClick = false
    private var disagreeClick = false
    private var next = false

    var id = 9999

    val My_READ_STORAGE_REQUEST_CODE = 1111
    val REQ_CODE_SELECT_IMAGE = 88

    var humanImgUri : String = ""

    var animalId = 9999

    var firstEditFlag = false

    var secondEditFlag = false

    private val customDialog : CustomSingleResDialog by lazy {
        CustomSingleResDialog(ApplyOnlineThirdTempOrAdoptActivity@ this, "필수 사항을 입력해주세요",responseListener, "확인")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_online_first)
        animalId = intent.getIntExtra("id", 9999)
        init()
        setBinding()

    }

    fun init(){
        applyOnlineFirstActiviyPresenter = ApplyOnlineFristActiviyPresenter()
        applyOnlineFirstActiviyPresenter.view = this
        applyOnlineFirstActiviyPresenter.initView()
        Glide.with(this@ApplyOnlineFirstActivity).load(ApplicationData.userImage).into(img_apply_first)
        tv_online_apply_first_name.text = ApplicationData.userName

    }

    private fun setBinding(){
        btn_online_apply_first_agree_check.clicks().subscribe {
            if(agreeClick){
                btn_online_apply_first_agree_check.setImageResource(R.drawable.adopt_1step_check_grey)
            }else{
                btn_online_apply_first_agree_check.setImageResource(R.drawable.adopt_1step_check_orange)

            }
            agreeClick = !agreeClick

            if (firstEditFlag == true && secondEditFlag == true && agreeClick == true) {
                btn_apply_online_first_next.setBackgroundColor(Color.parseColor("#ffc233"))
            }else {
                btn_apply_online_first_next.setBackgroundColor(Color.parseColor("#e2e2e2"))
            }
        }

        btn_online_apply_first_disagree_check.clicks().subscribe {
            if(disagreeClick){
                btn_online_apply_first_disagree_check.setImageResource(R.drawable.adopt_1step_check_grey)
            }else{
                btn_online_apply_first_disagree_check.setImageResource(R.drawable.adopt_1step_check_orange)
            }
            disagreeClick = !disagreeClick
        }

        btn_apply_online_first_next.clicks().subscribe {

            if(firstEditFlag == true && secondEditFlag == true){
                startActivity<ApplyOnlineSecondActivity>("address" to edit_online_apply_first_address.text.toString(), "job" to edit_online_apply_first_vocation.text.toString(), "humanImgUri" to humanImgUri, "id" to animalId)
            }else{
                //btn_apply_online_first_next.setBackgroundColor(Color.parseColor("#e2e2e2"))
                //팝업
                customDialog.show()
            }

            next = !next
        }

//        layout_apply_first_frame.clicks().subscribe{
//            requestReadExternalStoragePermission()
//        }
        btn_online_apply_first_modify_mypage.clicks().subscribe{
            if (firstEditFlag == true && secondEditFlag == true && agreeClick == true) {
                var intent = Intent(this@ApplyOnlineFirstActivity ,MypageSettingActivity::class.java)
                startActivityForResult(intent, 3003)
            }
        }
        btn_back_apply_first.clicks().subscribe{
            finish()
        }

        edit_online_apply_first_address.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(edit_online_apply_first_address.text.length >0){
                    firstEditFlag = true
                    if (firstEditFlag == true && secondEditFlag == true && agreeClick == true) {
                        btn_apply_online_first_next.setBackgroundColor(Color.parseColor("#ffc233"))
                    }
                }else{
                    firstEditFlag = false
                    btn_apply_online_first_next.setBackgroundColor(Color.parseColor("#e2e2e2"))
                }
            }
        })

        edit_online_apply_first_vocation.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(edit_online_apply_first_vocation.text.length >0) {
                    secondEditFlag = true
                    if (firstEditFlag == true && secondEditFlag == true && agreeClick == true) {
                        btn_apply_online_first_next.setBackgroundColor(Color.parseColor("#ffc233"))
                    }
                }else{
                    secondEditFlag= false
                    btn_apply_online_first_next.setBackgroundColor(Color.parseColor("#e2e2e2"))
                }
            }
        })

        root_view_apply_first.clicks().subscribe{
            val imm: InputMethodManager = applicationContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(root_view_apply_first.windowToken, 0)
        }
    }

    // 저장소 권한 확인
    private fun requestReadExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //사용자에게 권한을 왜 허용해야되는지에 메시지를 주기 위한 대한 로직을 추가하려면 이 블락에서 하면됩니다!! //하지만 우리는 그냥 비워놓습니다!! 딱히 줄말 없으면 비워놔도 무관해요!!! 굳이 뭐 안넣어도됩니다!
            } else {
                //아래 코드는 권한을 요청하는 메시지를 띄우는 기능을 합니다! 요청에 대한 결과는 callback으로 onRequestPermissionsResult 메소드에서 받습니다!!!
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), My_READ_STORAGE_REQUEST_CODE)
            }
        } else {
            //첫번째 if 문의 else 로써, 기존에 이미 권한 메시지를 통해 권한을 허용했다면 아래와 같은 곧바로 앨범을 여는 메소드를 호출 해주면됩니다!!
            showAlbum()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == My_READ_STORAGE_REQUEST_CODE ) {
            //결과에 대해 허용을 눌렀는지 체크하는 조건문이구요!
            if(grantResults.size > 0 && grantResults[0] == PackageManager. PERMISSION_GRANTED ){
                //이곳은 외부저장소 접근을 허용했을 때에 대한 로직을 쓰시면됩니다. 우리는 앨범을 여는 메소드를 호출해주면되겠죠?
                showAlbum()
            } else {
                //이곳은 외부저장소 접근 거부를 했을때에 대한 로직을 넣어주시면 됩니다.
                finish()
            }
        }
    }

    //앨범을 여는 메소드 //ACTION_PICK은 하나만 선택하는 것
    private fun showAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }


    //startActivityForResult를 통해 실행한 엑티비티에 대한 callback을 처리하는 메소드입니다!
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //REQUEST_CODE_SELECT_IMAGE를 통해 앨범에서 보낸 요청에 대한 Callback인지를 체크!!!
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                //data.data에는 앨범에서 선택한 사진의 Uri가 들어있습니다!! 그러니까 제대로 선택됐는지 null인지 아닌지를 체크!!!
                if (data != null) {

                    val selectedImageUri: Uri = data.data
                    humanImgUri = selectedImageUri.toString()
                    //Uri를 getRealPathFromURI라는 메소드를 통해 절대 경로를 알아내고, 인스턴스 변수 imageURI에 넣어줍니다!
                    //imageURI = getRealPathFromURI(selectedImageUri)

                    Glide.with(this@ApplyOnlineFirstActivity)
                            .load(selectedImageUri)
                            .thumbnail(0.1f)
                            .into(img_apply_first)
                }
            }
        }

        // 마이페이지 수정 다녀온 이후
        if(requestCode == 3003){
            Log.v("TAGG", "마이페이지 다녀옴")
            init()
        }
    }
    private val responseListener = View.OnClickListener { customDialog!!.dismiss() }
    fun initView(){

    }
}
