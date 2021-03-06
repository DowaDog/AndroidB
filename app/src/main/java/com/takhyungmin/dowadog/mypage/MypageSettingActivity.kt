package com.takhyungmin.dowadog.mypage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.mypage.model.MypageSettingGetObject
import com.takhyungmin.dowadog.mypage.model.MypageSettingObject
import com.takhyungmin.dowadog.mypage.model.get.GETMypageSettingResponse
import com.takhyungmin.dowadog.mypage.model.put.PUTMypageSettingResponse
import com.takhyungmin.dowadog.presenter.activity.MypageSettingActivityPresenter
import com.takhyungmin.dowadog.utils.ApplicationData
import kotlinx.android.synthetic.main.activity_mypage_setting.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class MypageSettingActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            img_profile_mypage_set_act -> {
                // 갤러리 접근 권한 확인 기능
                requestReadExternalStoragePermission()
            }
            img_camera_mypage_set_act -> {
                // 갤러리 접근 권한 확인 기능
                requestReadExternalStoragePermission()
            }
            rl_mypage_setting_act -> {
                downKeyboard(rl_mypage_setting_act)
            }
            //취소버튼
            btn_cancle_mypage_setting_act -> {
                //기존의 정보로 초기화시키고
                finish()
            }
            //확인버튼
            btn_confirm_mypage_setting_act -> {

                //put request
                // editText로 작성한 것을 put
                checkFill()

                //finish()
            }

            et_name_mod_mypage_setting_act -> {
                gravityRightNameHint()
            }

        }
    }

    private val REQ_CODE_SELECT_IMAGE = 1
    val My_READ_STORAGE_REQUEST_CODE = 7777
    lateinit var data: Uri

    private lateinit var mypageSettingActivityPresenter: MypageSettingActivityPresenter

    lateinit var mypagePutdata : PUTMypageSettingResponse
    private var mimage: MultipartBody.Part? = null

    var name : String = ApplicationData.userName
    var birth : String = ApplicationData.userBirth
    var phone : String = ApplicationData.userPhone
    var image : String = "asdlkjds"

    var imageURI : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_setting)

        init()
        initPresenter()

        mypageSettingActivityPresenter.initView()
        //get request
        mypageSettingActivityPresenter.requestGetData(mimage)

    }

    fun init() {
        img_profile_mypage_set_act.setOnClickListener(this)
        img_camera_mypage_set_act.setOnClickListener(this)

        setOnEnterListener()

        //et_name 수정
        et_name_mod_mypage_setting_act.setOnClickListener(this)

        //취소 버튼
        btn_cancle_mypage_setting_act.setOnClickListener(this)

        //확인버튼
        btn_confirm_mypage_setting_act.setOnClickListener(this)

        //키보드 내려가게 하는 함수
        rl_mypage_setting_act.setOnClickListener(this)

        et_name_mod_mypage_setting_act.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s!!.toString()
            }

        })

        et_phone_mod_mypage_setting_act.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                phone = s!!.toString()
            }

        })

        et_birth_mod_mypage_setting_act.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                phone = s!!.toString()
            }

        })


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

                    //Uri를 getRealPathFromURI라는 메소드를 통해 절대 경로를 알아내고, 인스턴스 변수 imageURI에 넣어줍니다!
                    //imageURI = getRealPathFromURI(selectedImageUri)

                    this.data = data!!.data

                    val selectedImageUri: Uri = data.data
                    val options = BitmapFactory.Options()
                    var inputstream: InputStream? = contentResolver.openInputStream(selectedImageUri)  // here, you need to get your context.
                    val bitmap = BitmapFactory.decodeStream(inputstream, null, options)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())

                    mimage = MultipartBody.Part.createFormData("profileImgFile", File(selectedImageUri.toString()).name, photoBody)


                    Glide.with(this@MypageSettingActivity)
                            .load(selectedImageUri)
                            .thumbnail(0.1f)
                            .into(img_profile_mypage_set_act)
                }
            }
        }
    }

    private fun downKeyboard(view : View) {
        val imm: InputMethodManager = applicationContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun upKeyboard(view : View) {
//        val imm : InputMethodManager =applicationContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(view, 0)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun gravityRightNameHint(){
        et_name_mod_mypage_setting_act.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                    et_name_mod_mypage_setting_act.gravity = Gravity.LEFT
                }
                else{
                    et_name_mod_mypage_setting_act.gravity = Gravity.RIGHT
                }
            }
        })
    }

    fun responseGetData(data : GETMypageSettingResponse) {
        data?.let{

            image = data.data.thumbnailImg
            name = data.data.name
            birth = data.data.birth
            phone = data.data.phone

            et_name_mod_mypage_setting_act.hint = name
            et_birth_mod_mypage_setting_act.hint = birth
            et_phone_mod_mypage_setting_act.hint = phone
            Glide.with(this).load(image).into(img_profile_mypage_set_act)

        }
    }

    //회원정보 수정
    fun responseData(data: PUTMypageSettingResponse) {

        data?.let {

            mypagePutdata = data
            //여기에 받아온 데이터들을 가져와서 보여주는 것을 해야함 (함수로 만들던 여기에 구현하던)
            //Log.v("yyg", mypagePutdata.message)
            //startActivity<MypageActivity>()
            if(!et_name_mod_mypage_setting_act.text.isEmpty())
                ApplicationData.userName = et_name_mod_mypage_setting_act.text.toString()
            if(!et_birth_mod_mypage_setting_act.text.isEmpty())
                ApplicationData.userBirth = et_birth_mod_mypage_setting_act.text.toString()
            if(!et_phone_mod_mypage_setting_act.text.isEmpty())
                ApplicationData.userPhone = et_phone_mod_mypage_setting_act.text.toString()
            if(mimage != null)
                ApplicationData.userImage = this.data.toString()

            finish()
        }
    }

    //view에 presenter 붙여주기
    private fun initPresenter() {

        mypageSettingActivityPresenter = MypageSettingActivityPresenter()
        // 뷰 붙여주는 작업
        mypageSettingActivityPresenter.view = this

        MypageSettingObject.mypageSettinActivityPresenter = mypageSettingActivityPresenter

        MypageSettingGetObject.mypageSettingGETActivityPresenter = mypageSettingActivityPresenter

        Log.v("TAGG", "mypagesetting 엑티비티 이닛프레젠터")

    }

    fun getEditText(){

    }

    fun checkFill(){

//        val selectedPictureUri = Uri.parse(image)
//        val options = BitmapFactory.Options()
//
//        var input: InputStream? = null // here, you need to get your context.
//        try {
//            input = contentResolver.openInputStream(selectedPictureUri)
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
//
//        val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
//        val baos = ByteArrayOutputStream()
//        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, baos)
//        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
//        mimage = MultipartBody.Part.createFormData("profileImgFile", "", photoBody)
//        Glide.with(this).load(selectedPictureUri).thumbnail(0.1f).thumbnail(0.1f).into(img_profile_sign_id_set_act)


        mypageSettingActivityPresenter.requestData(name, birth, phone, mimage)
        //finish()

    }

    fun setOnEnterListener() {
        et_phone_mod_mypage_setting_act.setOnKeyListener { v, keyCode, event ->
            if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                btn_confirm_mypage_setting_act.performClick()
                true
            }
            false
        }
    }

}


/*
    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }
*/

