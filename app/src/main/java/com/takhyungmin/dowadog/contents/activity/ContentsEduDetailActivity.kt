package com.takhyungmin.dowadog.contents.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.contents.ContentsObject
import com.takhyungmin.dowadog.contents.adapter.ContentsEduDetailItem
import com.takhyungmin.dowadog.contents.adapter.ContentsEduDetailRvAdapter
import com.takhyungmin.dowadog.contents.model.ContentEduDetailObject
import com.takhyungmin.dowadog.contents.model.get.GETContentsEduDetailResponse
import com.takhyungmin.dowadog.login.LoginActivity
import com.takhyungmin.dowadog.presenter.activity.ContentsEduDetailActivityPresenter
import com.takhyungmin.dowadog.utils.ApplicationData
import com.takhyungmin.dowadog.utils.CustomAllCompleteDialog
import com.takhyungmin.dowadog.utils.CustomDialog
import com.takhyungmin.dowadog.utils.CustomPartlyCompleteDogDialog
import kotlinx.android.synthetic.main.activity_contents_edu_detail.*

class ContentsEduDetailActivity : AppCompatActivity() {

    private lateinit var contentsEduDetailActivityPresenter: ContentsEduDetailActivityPresenter
    private lateinit var contentsEduDetailRvAdapter: ContentsEduDetailRvAdapter
    private lateinit var requestManager : RequestManager
    private lateinit var eduCompleteCustomDialog : CustomPartlyCompleteDogDialog
    private lateinit var eduAllCompleteDogDialog: CustomAllCompleteDialog
    var id = 0
    var eduCount = 0
    var eduCompleted = false
    var eduAllCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        //ActivityCompat.setEnterSharedElementCallback(this, EnterTransitionCallback)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //setTheme(R.style.ContentsDetailActivityBasic)
        //window.statusBarColor = Color.TRANSPARENT
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_edu_detail)
        //Glide.with(this).load(R.drawable.pic1).into(img_contents_edu_detail)
        tv_contents_edu_detail_title.text = intent.getStringExtra("title")
        rv_contents_edu_detail_content.setFocusable(false)
        layout_edu_detail.requestFocus()

        if(intent.getBooleanExtra("from", false)){
            btn_contents_edu_detail_complete_frame.visibility = View.GONE
        }

        init()

    }

    private fun init(){
        contentsEduDetailActivityPresenter = ContentsEduDetailActivityPresenter()
        contentsEduDetailActivityPresenter.view = this
        ContentEduDetailObject.contentsEduDetailActivityPresenter = contentsEduDetailActivityPresenter
        ContentsObject.current = 0
        id = intent.getIntExtra("id", 20)


        //Glide.with(this).load(intent.getStringExtra("image")).into(img_contents_edu_detail)
        contentsEduDetailActivityPresenter.requestData(id)


//        if(intent.getBooleanExtra("edu", false))
//            btn_contents_edu_detail_complete_frame.visibility = View.GONE

        tv_contents_edu_detail_title.text = intent.getStringExtra("title")
        responseScrap(intent.getBooleanExtra("scrap", false))

        //contentsEduDetailActivityPresenter.initView()
        initPresenter()
        setScrollListener()
        setOnBinding()

    }

    fun initView(contentsEduDetailItems : ArrayList<ContentsEduDetailItem>){
        requestManager = Glide.with(this)
        //contentsEduDetailRvAdapter = ContentsEduDetailRvAdapter(contentsEduDetailItems, requestManager)
        rv_contents_edu_detail_content.layoutManager = LinearLayoutManager(this)
        rv_contents_edu_detail_content.adapter = contentsEduDetailRvAdapter
    }

    private fun setScrollListener(){
        sv_contents_edu_detail_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY + 30) {
                //scroll down
                window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                layout_contents_edu_detail_toolbar_move.visibility = View.GONE
                layout_contents_edu_detail_toolbar_basic.visibility = View.GONE
                if(scrollY > img_contents_edu_detail.height){
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }
            if (scrollY < oldScrollY - 30) {
                //scroll up
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                window.statusBarColor = this.resources.getColor(R.color.status2)
                layout_contents_edu_detail_toolbar_move.visibility = View.VISIBLE
                if(scrollY < img_contents_edu_detail.height){
                    window.decorView.systemUiVisibility = 0
                    window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    layout_contents_edu_detail_toolbar_basic.visibility = View.VISIBLE
                    layout_contents_edu_detail_toolbar_move.visibility = View.GONE
                }
            }

            if (scrollY == 0) {
                //scroll in top
//                window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//                layout_contents_edu_detail_toolbar_basic.visibility = View.VISIBLE
//                layout_contents_edu_detail_toolbar_move.visibility = View.GONE
            }

            if (scrollY == ( v.getChildAt(0).height - v.height )) {
                //scroll in bottom
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                window.statusBarColor = this.resources.getColor(R.color.status2)
                layout_contents_edu_detail_toolbar_move.visibility = View.VISIBLE
            }
        })
    }

//    private val EnterTransitionCallback = object : SharedElementCallback() {
//        @SuppressLint("NewApi")
//        override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
//            sharedElements.put(names[0], img_contents_edu_detail)
//        }
//    }


    fun setOnBinding(){
        btn_contents_edu_detail_back1.clicks().subscribe {
            finish()
        }

        btn_contents_edu_detail_back2.clicks().subscribe {
            finish()
        }

        btn_contents_edu_detail_scrap1.clicks().subscribe {
            if(ApplicationData.auth == "")
                logoutCustomDialog.show()
            else{
                contentsEduDetailActivityPresenter.requestEduScrap(id)
            }
        }

        btn_contents_edu_detail_scrap2.clicks().subscribe {
            if(ApplicationData.auth == ""){
                logoutCustomDialog.show()
            }else
                contentsEduDetailActivityPresenter.requestEduScrap(id)
        }

        btn_contents_edu_detail_complete.clicks().subscribe {
            if(ApplicationData.auth == "")
                logoutCustomDialog.show()
            else{
                if(intent.getBooleanExtra("edu", false)){
                    eduAllCompleteDogDialog = CustomAllCompleteDialog(this,"", responseListener1, "확인")
                    eduAllCompleteDogDialog.show()
                }
                else
                    contentsEduDetailActivityPresenter.requestComplete(id)
            }
        }
    }

    private val responseListener1 = View.OnClickListener { eduAllCompleteDogDialog.dismiss() }








    fun responseData(data: GETContentsEduDetailResponse) {

        data?.let {

            requestManager = Glide.with(this)

            var setContentsEduDetailAdapter = ContentsEduDetailRvAdapter(it.data.content, requestManager)
            //여기에 받아온 데이터들을 가져와서 보여주는 것을 해야함 (함수로 만들던 여기에 구현하던)
            Log.v("ygyg", it.data.content.toString())

            if(data.data.edu.allComplete != null){
                eduCompleted = data.data.edu.allComplete
            }

            if(data.data.edu.allEducate != null){
                eduAllCount = data.data.edu.allEducate
            }
            //eduCount = data.data.edu.userEducate


            Glide.with(this).load(data.data.cardnewsThumbnail).into(img_contents_edu_detail)

            rv_contents_edu_detail_content.adapter = setContentsEduDetailAdapter
            rv_contents_edu_detail_content.layoutManager = LinearLayoutManager(this)

            rv_contents_edu_detail_content.setNestedScrollingEnabled(false)

        }
    }

    //view에 presenter 붙여주기
    private fun initPresenter() {

        contentsEduDetailActivityPresenter= ContentsEduDetailActivityPresenter()
        // 뷰 붙여주는 작업
        contentsEduDetailActivityPresenter.view = this
        ContentEduDetailObject.contentsEduDetailActivityPresenter = contentsEduDetailActivityPresenter

        Log.v("TAGG", "EduDetail 엑티비티 이닛프레젠터")

    }

    fun responseScrap(clear : Boolean){
        if(clear){
            btn_contents_edu_detail_scrap1_1.setImageResource(R.drawable.contents_scrap_btn)
            btn_contents_edu_detail_scrap2_1.setImageResource(R.drawable.contents_scrap_btn)
        }else{
            btn_contents_edu_detail_scrap1_1.setImageResource(R.drawable.contents_unscrap_btn)
            btn_contents_edu_detail_scrap2_1.setImageResource(R.drawable.contents_unscrap_btn)
        }
    }

    val logoutCustomDialog : CustomDialog  by lazy {
        CustomDialog(this, "로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?", responseRight, responseLeft,"취소", "확인")
    }

    private val responseRight = View.OnClickListener {

        logoutCustomDialog!!.dismiss()
    }
    private val responseLeft = View.OnClickListener {
        startActivity(Intent(this, LoginActivity::class.java))
        logoutCustomDialog!!.dismiss()
        //##로그아웃
    }


    fun responseComplete(clear : Boolean){
        if(clear){
            val num = intent.getIntExtra("num", 0) + 1
            //val num = eduCount + 1
            eduCompleteCustomDialog = CustomPartlyCompleteDogDialog(this, num.toString(), responseListener, "확인")
            eduCompleteCustomDialog.show()

        }
    }

    private val responseListener = View.OnClickListener {
        finish()
        eduCompleteCustomDialog.dismiss() }
}
