package com.takhyungmin.dowadog.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.BaseActivity
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.adoptedanimal.AdoptedAnimalActivity
import com.takhyungmin.dowadog.interest.InterestAnimalActivity
import com.takhyungmin.dowadog.letter.LetterActivity
import com.takhyungmin.dowadog.login.LoginActivity
import com.takhyungmin.dowadog.mypage.model.Data
import com.takhyungmin.dowadog.mypage.model.MypageObject
import com.takhyungmin.dowadog.mypage.model.get.GETMypageResponse
import com.takhyungmin.dowadog.presenter.activity.MypageActivityPresenter
import com.takhyungmin.dowadog.scrap.MyCommunityPostActivity
import com.takhyungmin.dowadog.scrap.ScrapActivity
import com.takhyungmin.dowadog.utils.ApplicationData
import com.takhyungmin.dowadog.utils.CustomDialog
import com.takhyungmin.dowadog.utils.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            //로그아웃 버튼
            btn_logout_setting_my_page_act -> {
                logoutCustomDialog!!.show()
            }
            //마이페이지 설정
            btn_next_setting_mypage_act -> {
                if(ApplicationData.auth == ""){
                    loginCustomDialog.show()
                }else{
                    startActivityForResult(Intent(this, MypageSettingActivity::class.java), 3333)
                }

            }
            btn_back_mypage_act -> {
                finish()
            }
            //우체통 버튼
            btn_post_letter_mypage_act -> {
                startActivityForResult(Intent(this, LetterActivity::class.java), 5555)
                //startActivity<LetterActivity>()
            }

            btn_adopt_animal_mypage_act -> {
                //startActivity<>()
            }

        }
    }

    val logoutCustomDialog: CustomDialog  by lazy {
        CustomDialog(this@MypageActivity, "로그아웃 하시겠습니까?", responseRight, responseLeft, "취소", "확인")
    }

    val loginCustomDialog: CustomDialog  by lazy {
        CustomDialog(this@MypageActivity, "로그인이 필요한 서비스입니다.\n" +
                "로그인 하시겠습니까?", responseRight2, responseLeft2, "취소", "확인")
    }

    private val responseRight2 = View.OnClickListener {

        logoutCustomDialog!!.dismiss()
    }

    private val responseLeft2 = View.OnClickListener {
        startActivity(Intent(this, LoginActivity::class.java))
        logoutCustomDialog!!.dismiss()
        //##로그아웃
    }

    private lateinit var mypageActivityPresenter: MypageActivityPresenter

    lateinit var mypageGetData: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        init()
        setOnBinding()

        initPresenter()
        mypageActivityPresenter.initView()
        mypageActivityPresenter.requestData()

    }

    //JAX-RS문법..?
    fun setOnBinding() {
        btn_mypage_interest.clicks().subscribe {
            startActivity(Intent(this, InterestAnimalActivity::class.java))
        }

        btn_mypage_scrap.clicks().subscribe {
            startActivity(Intent(this, ScrapActivity::class.java))
        }
        //내가 쓴 글
        btn_mypage_mine.clicks().subscribe {
            //startActivity(Intent(this, MyCommunityPostActivity::class.java))

            startActivityForResult(Intent(this, MyCommunityPostActivity::class.java), 3333)
            //val intent = Intent(this, MyCommunityPostActivity::class.java)
        }
        //입양한 우리아이
        btn_adopt_animal_mypage_act.clicks().subscribe {
            startActivity(Intent(this, AdoptedAnimalActivity::class.java))

        }
    }

    private fun init() {
        btn_logout_setting_my_page_act.setOnClickListener(this)
        btn_next_setting_mypage_act.setOnClickListener(this)
        btn_back_mypage_act.setOnClickListener(this)
        btn_post_letter_mypage_act.setOnClickListener(this)
    }

    private val responseRight = View.OnClickListener {
        logoutCustomDialog!!.dismiss()
    }
    private val responseLeft = View.OnClickListener {

        logoutCustomDialog!!.dismiss()
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("splash", 1)
        //val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        SharedPreferenceController.setRefreshToken(this, "")

        startActivity(intent)
        //##로그아웃
    }

    fun responseData(data: GETMypageResponse) {

        data?.let {

            Log.v("yyg", "ygy22222")

            mypageGetData = data.data
            //여기에 받아온 데이터들을 가져와서 보여주는 것을 해야함 (함수로 만들던 여기에 구현하던)
            Log.v("TAGG", mypageGetData.toString())

            Log.v("yyg", mypageGetData.userName)

            Glide.with(this@MypageActivity)
                    .load(mypageGetData.profileImg)
                    .thumbnail(0.1f)
                    .into(img_profile_mypage_act)

            tv_username_mypage_act.text = mypageGetData.userName + "님"
            tv_interest_animal_mypage_act.text = mypageGetData.userLike.toString()
            tv_scrap_num_mypage_act.text = mypageGetData.userScrap.toString()
            tv_write_num_mypage_act.text = mypageGetData.userCommunity.toString()
            if (mypageGetData.mailboxUpdated) {
                img_new_mypage_act.visibility = View.VISIBLE
            } else {
                img_new_mypage_act.visibility = View.INVISIBLE
            }
        }
    }

    //view에 presenter 붙여주기
    private fun initPresenter() {

        mypageActivityPresenter = MypageActivityPresenter()
        // 뷰 붙여주는 작업
        mypageActivityPresenter.view = this
        MypageObject.mypageActivityPresenter = mypageActivityPresenter

        Log.v("TAGG", "엑티비티 이닛프레젠터")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.v("yyg", "code" + requestCode)
        if (requestCode == 3333) {
            Log.v("yyg", "ygy")
            mypageActivityPresenter.requestData()
        }

        if (requestCode == 5555) {
            img_new_mypage_act.visibility = View.INVISIBLE
        }

    }

}
