package com.takhyungmin.dowadog.apply.online

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.ApplyOnlineThirdTempOrAdoptActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.utils.CustomSingleResDialog
import kotlinx.android.synthetic.main.activity_apply_online_second.*
import org.jetbrains.anko.startActivity

class ApplyOnlineSecondActivity : AppCompatActivity() {

    private val customDialog : CustomSingleResDialog by lazy {
        CustomSingleResDialog(ApplyOnlineThirdTempOrAdoptActivity@ this, "필수 사항을 입력해주세요",responseListener, "확인")
    }

    var address : String = ""
    var job : String = ""
    var humanImgUri : String = ""
    var animalId = 9999

    var animalImgUri : String = ""

    var animalDescription = ""

    val My_READ_STORAGE_REQUEST_CODE = 1111
    val REQ_CODE_SELECT_IMAGE = 88

    var havePetFlag = false
    var isSelect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_online_second)
        getIntentItem()
        setOnBinding()

    }

    fun setOnBinding(){
        btn_online_apply_second_agree_check.clicks().subscribe {
            btn_online_apply_second_disagree_check.setImageResource(R.drawable.adopt_1step_check_grey)
            btn_online_apply_second_agree_check.setImageResource(R.drawable.adopt_1step_check_orange)
            layout_apply_second_input.visibility = View.VISIBLE
            btn_apply_online_second_next.setBackgroundColor(Color.parseColor("#ffc233"))
            havePetFlag = true
            isSelect = true
        }

        btn_online_apply_second_disagree_check.clicks().subscribe {
            btn_online_apply_second_agree_check.setImageResource(R.drawable.adopt_1step_check_grey)
            btn_online_apply_second_disagree_check.setImageResource(R.drawable.adopt_1step_check_orange)
            layout_apply_second_input.visibility = View.INVISIBLE
            btn_apply_online_second_next.setBackgroundColor(Color.parseColor("#ffc233"))
            havePetFlag = false
            isSelect = true
        }

        btn_apply_online_second_next.clicks().subscribe{
            if(et_animal_description.text.toString().length != 0){
                animalDescription = et_animal_description.text.toString()
            }
            if(isSelect == true){
                startActivity<ApplyOnlineThirdTempOrAdoptActivity>("address" to address, "job" to job, "humanImgUri" to humanImgUri,
                        "animalDescription" to animalDescription, "animalImgUri" to animalImgUri, "id" to animalId, "havePet" to havePetFlag)
            }else {
                customDialog.show()
            }

        }

        layout_apply_second_frame.clicks().subscribe{
            requestReadExternalStoragePermission()
        }

        btn_apply_second_back_real.clicks().subscribe{
            finish()
        }
    }

    fun getIntentItem(){
        address = intent.getStringExtra("address")
        job = intent.getStringExtra("job")
        humanImgUri = intent.getStringExtra("humanImgUri")
        animalId = intent.getIntExtra("id", 9999)
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
                    animalImgUri = selectedImageUri.toString()
                    //Uri를 getRealPathFromURI라는 메소드를 통해 절대 경로를 알아내고, 인스턴스 변수 imageURI에 넣어줍니다!
                    //imageURI = getRealPathFromURI(selectedImageUri)

                    Glide.with(this@ApplyOnlineSecondActivity)
                            .load(selectedImageUri)
                            .thumbnail(0.1f)
                            .into(img_apply_second)
                }
            }
        }
    }


    private val responseListener = View.OnClickListener { customDialog!!.dismiss() }
}
