package com.takhyeongmin.dowadogbeta.modifyinfoanimal

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.modifyinfoanimal.model.ModifyInfoAnimalObject
import com.takhyungmin.dowadog.modifyinfoanimal.model.get.AnimalUserAdopt
import com.takhyungmin.dowadog.modifyinfoanimal.model.get.GETModifyInfoAnimalResponse
import com.takhyungmin.dowadog.modifyinfoanimal.model.get.Inoculation
import com.takhyungmin.dowadog.modifyinfoanimal.model.put.PUTModifyInfoAnimalResponse
import com.takhyungmin.dowadog.presenter.activity.ModifyInfoAnimalActivityPresenter
import com.takhyungmin.dowadog.utils.ApplicationData
import kotlinx.android.synthetic.main.activity_modify_info_animal.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class ModifyInfoAnimalActivity : BaseActivity(), View.OnClickListener {

    private var cutFlag: Int = 0
    private var firstFlag: Int = 0
    private var secondFlag: Int = 0
    private var thirdFlag: Int = 0
    private var fourthFlag: Int = 0
    private var fifthFlag: Int = 0
    private var sixthFlag: Int = 0

    private val REQ_CODE_SELECT_IMAGE = 1
    val My_READ_STORAGE_REQUEST_CODE = 7777
    lateinit var data: Uri

    var animalId: Int = 0

    var name: String = ApplicationData.userName
    var weight: String = "3"
    var neuterYn: Boolean = false
    var age: String = "4"
    var gender : String = "F"
    var kind : String = "품"

    private lateinit var modifyInfoAnimalActivityPresenter: ModifyInfoAnimalActivityPresenter

    lateinit var modifyGetData: AnimalUserAdopt
    lateinit var modifyGetInoculation: ArrayList<Inoculation>

    private var mimage: MultipartBody.Part? = null

    private var inoculatio : ArrayList<String> = ArrayList()

    var id: Int = 20

    override fun onClick(v: View?) {
        when (v) {

            // 중성화 버튼
            btn_dog_cut_do_check_modify_info_animal_act -> {
                if (cutFlag == 0) {
                    iv_dog_cut_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    cutFlag = 1
                    neuterYn = true
                    iv_dog_cut_dont_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    Log.v("TAG", "4")
                } else {
                    iv_dog_cut_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    cutFlag = 0
                    neuterYn = false
                    Log.v("TAG", "3")
                }
            }

            //중성화 하지 않음 버튼
            btn_dog_cut_dont_check_modify_info_animal_act -> {
                if (cutFlag == 1) {
                    //체크 된 상태
                    iv_dog_cut_dont_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    cutFlag = 0
                    neuterYn = false
                    Log.v("TAG", "1")
                    iv_dog_cut_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                } else {
                    //체크 안 된 상태
                    iv_dog_cut_dont_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    cutFlag = 1
                    neuterYn = true
                    Log.v("TAG", "2")
                }

            }

            // 종합백신 7종
            btn_dog_first_do_check_modify_info_animal_act -> {
                if (sixthFlag == 1) {
                    //종합백신 체크

                    iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                }
                if (firstFlag == 0) {
                    inoculatio.add(("I1"))
                    iv_dog_first_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    firstFlag = 1
                } else {
                    inoculatio.remove(("I1"))
                    iv_dog_first_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    firstFlag = 0
                }

            }

            // 광견병
            btn_dog_second_do_check_modify_info_animal_act -> {
                if (sixthFlag == 1) {
                    iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                }
                if (secondFlag == 0) {
                    inoculatio.add(("I2"))
                    iv_dog_second_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    secondFlag = 1
                } else {
                    inoculatio.remove(("I2"))
                    iv_dog_second_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    secondFlag = 0
                }

            }

            // 심장 사상충
            btn_dog_third_do_check_modify_info_animal_act -> {
                if (sixthFlag == 1) {
                    iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                }
                if (thirdFlag == 0) {
                    inoculatio.add(("I3"))
                    iv_dog_third_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    thirdFlag = 1
                } else {
                    inoculatio.remove(("I3"))
                    iv_dog_third_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    thirdFlag = 0
                }

            }

            // 코로나 장염
            btn_dog_fourth_do_check_modify_info_animal_act -> {
                if (sixthFlag == 1) {
                    iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                }
                if (fourthFlag == 0) {
                    inoculatio.add(("I4"))
                    iv_dog_fourth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    fourthFlag = 1
                } else {
                    inoculatio.remove(("I4"))
                    iv_dog_fourth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    fourthFlag = 0
                }

            }

            // 캔넬코프
            btn_dog_fiveth_do_check_modify_info_animal_act -> {
                if (sixthFlag == 1) {
                    iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                }
                if (fifthFlag == 0) {
                    inoculatio.add(("I5"))
                    iv_dog_fiveth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    fifthFlag = 1
                } else {
                    inoculatio.remove(("I5"))
                    iv_dog_fiveth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    fifthFlag = 0
                }
            }
            // 접종을 아예 하지 않았습니다.
            btn_dog_sixth_do_check_modify_info_animal_act -> {
                if (sixthFlag == 0) {
                    iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                    sixthFlag = 1
                    iv_dog_first_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    firstFlag = 0
                    iv_dog_second_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    secondFlag = 0
                    iv_dog_third_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    thirdFlag = 0
                    iv_dog_fourth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    fifthFlag = 0
                    iv_dog_fiveth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    inoculatio.clear()
                } else {
                    iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                    sixthFlag = 0
                }
            }

            // 사진
            btn_my_dog_modify_info_animal_act -> {
                requestReadExternalStoragePermission()
            }

            // 취소 버튼
            btn_cancle_modify_info_animal_act -> {
                finish()
            }

            btn_confirm_modify_info_animal_act -> {
                // ## 확인버튼
                //통신
                if (!et_dog_name_modify_info_animal_act.text.isEmpty()){
                    name = et_dog_name_modify_info_animal_act.text.toString()
                }else{
                    name = modifyGetData.name
                }
                if (!et_dog_birth_modify_info_animal_act.text.isEmpty())
                    age = et_dog_birth_modify_info_animal_act.text.toString()
                else
                    age = modifyGetData.age
                if(!et_dog_birth_weight_info_animal_act.text.isEmpty())
                    weight = et_dog_birth_weight_info_animal_act.text.toString()
                else
                    weight = modifyGetData.weight


                modifyInfoAnimalActivityPresenter.PUTModifyrequestData(animalId, name, modifyGetData.kind ,weight, neuterYn, age, mimage, modifyGetData.gender, inoculatio)

            }

        // 생년월일 박스
            rl_dog_birth_modify_info_animal_act -> {
                et_dog_birth_modify_info_animal_act.isSelected
            }

            // 무게 박스
            rl_dog_weight_modify_info_animal_act -> {
                et_dog_birth_weight_info_animal_act.isSelected
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_info_animal)
        init()
        initPresenter()

        modifyInfoAnimalActivityPresenter = ModifyInfoAnimalActivityPresenter()
        modifyInfoAnimalActivityPresenter.view = this

        animalId = intent.getIntExtra("animalId", 0)
        modifyInfoAnimalActivityPresenter.GETModifyrequestData(animalId)
    }

    private fun init() {
        btn_dog_cut_do_check_modify_info_animal_act.setOnClickListener(this)
        btn_dog_cut_dont_check_modify_info_animal_act.setOnClickListener(this)
        btn_dog_first_do_check_modify_info_animal_act.setOnClickListener(this)
        btn_dog_second_do_check_modify_info_animal_act.setOnClickListener(this)
        btn_dog_third_do_check_modify_info_animal_act.setOnClickListener(this)
        btn_dog_fourth_do_check_modify_info_animal_act.setOnClickListener(this)
        btn_dog_fiveth_do_check_modify_info_animal_act.setOnClickListener(this)
        btn_dog_sixth_do_check_modify_info_animal_act.setOnClickListener(this)
        btn_cancle_modify_info_animal_act.setOnClickListener(this)
        btn_my_dog_modify_info_animal_act.setOnClickListener(this)
        btn_confirm_modify_info_animal_act.setOnClickListener(this)
        rl_dog_birth_modify_info_animal_act.setOnClickListener(this)
        rl_dog_weight_modify_info_animal_act.setOnClickListener(this)
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
        if (requestCode == My_READ_STORAGE_REQUEST_CODE) {
            //결과에 대해 허용을 눌렀는지 체크하는 조건문이구요!
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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


                    Glide.with(this@ModifyInfoAnimalActivity)
                            .load(selectedImageUri)
                            .thumbnail(0.1f)
                            .into(iv_my_dog_modify_info_animal_act)
                }
            }
        }
    }

    fun responseData(data: GETModifyInfoAnimalResponse) {

        data?.let {



            modifyGetData = it.data.animalUserAdopt
            modifyGetInoculation = it.data.inoculationList

            Log.v("ygy", "come in")
            Log.v("ygy", modifyGetData.name)
            Log.v("ygy", modifyGetData.kind)
            Log.v("ygy", modifyGetData.age)
            //여기에 받아온 데이터들을 가져와서 보여주는 것을 해야함 (함수로 만들던 여기에 구현하던)

            Glide.with(this@ModifyInfoAnimalActivity)
                    .load(modifyGetData.profileImg)
                    .thumbnail(0.1f)
                    .into(iv_my_dog_modify_info_animal_act)

            var getname = modifyGetData.name

            et_dog_name_modify_info_animal_act.hint = modifyGetData.name
            tv_dog_kind_right_modify_info_animal_act.text = modifyGetData.kind
            et_dog_birth_modify_info_animal_act.hint = modifyGetData.age
            et_dog_birth_weight_info_animal_act.hint = modifyGetData.weight



            if (modifyGetData.neuterYn) {
                iv_dog_cut_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                iv_dog_cut_dont_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                cutFlag = 1
                neuterYn = true

            } else {
                iv_dog_cut_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                iv_dog_cut_dont_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                cutFlag = 0
                neuterYn = false
            }

            if (modifyGetData.gender == "M") {
                tv_dog_sex_right_modify_info_animal_act.text = "수컷"
            } else {
                tv_dog_sex_right_modify_info_animal_act.text = "암컷"
            }

            //종합백신 7종
            if (modifyGetInoculation[0].complete) {

                iv_dog_first_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                firstFlag = 1
                inoculatio.add("I1")
            } else {
                iv_dog_first_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                firstFlag = 0
            }

            //광견병
            if (modifyGetInoculation[1].complete) {
                iv_dog_second_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                secondFlag = 1
                inoculatio.add("I2")
            } else {
                iv_dog_second_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                secondFlag = 0
            }

            // 심장 사상충
            if (modifyGetInoculation[2].complete) {
                iv_dog_third_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                thirdFlag = 1
                inoculatio.add("I3")
            } else {
                iv_dog_third_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                thirdFlag = 0
            }

            //코로나 장염
            if (modifyGetInoculation[3].complete) {
                iv_dog_fourth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                fourthFlag = 1
                inoculatio.add("I4")
            } else {
                iv_dog_fourth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                fourthFlag = 0
            }

            //캔넬코프
            if (modifyGetInoculation[4].complete) {
                iv_dog_fiveth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
                fifthFlag = 1
                inoculatio.add("I5")
            } else {
                iv_dog_fiveth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
                fifthFlag = 0
            }

            if (modifyGetInoculation[0].complete && modifyGetInoculation[1].complete && modifyGetInoculation[2].complete && modifyGetInoculation[3].complete && modifyGetInoculation[4].complete) {
                iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_orange)
            } else {
                iv_dog_sixth_do_check_modify_info_animal_act.setImageResource(R.drawable.b_check_grey)
            }
        }
    }

    //view에 presenter 붙여주기
    private fun initPresenter() {

        modifyInfoAnimalActivityPresenter = ModifyInfoAnimalActivityPresenter()
        // 뷰 붙여주는 작업
        modifyInfoAnimalActivityPresenter.view = this
        ModifyInfoAnimalObject.modifyInfoAnimalActivityPresenter = modifyInfoAnimalActivityPresenter

        Log.v("TAGG", "modifyInfoAnimal 엑티비티 이닛프레젠터")
    }

    //회원정보 수정
    fun PutResponseData(data: PUTModifyInfoAnimalResponse) {
        Log.v("request", "response1")

        data?.let {

            Log.v("request", "response2")
            //여기에 받아온 데이터들을 가져와서 보여주는 것을 해야함 (함수로 만들던 여기에 구현하던)
            //Log.v("yyg", mypagePutdata.message)
            //startActivity<MypageActivity>()
            if (!et_dog_name_modify_info_animal_act.text.isEmpty())
                name = et_dog_name_modify_info_animal_act.text.toString()
            if (!et_dog_birth_modify_info_animal_act_tv.text.isEmpty())
                age = et_dog_birth_modify_info_animal_act_tv.text.toString()
            if(!et_dog_birth_weight_info_animal_act.text.isEmpty())
                weight = et_dog_birth_weight_info_animal_act.text.toString()

            //중성화 여부
            neuterYn = (cutFlag == 1)
//            if(cutFlag ==1)
//            {
//                neuterYn = true
//            }else
//            {
//                neuterYn = false
//            }



            finish()

        }
    }

    fun dataChange(){

        name = et_dog_name_modify_info_animal_act.text.toString()
        age = et_dog_birth_modify_info_animal_act.text.toString()
        weight = et_dog_birth_weight_info_animal_act.text.toString()


    }
}