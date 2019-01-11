package com.takhyungmin.dowadog.adopt.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.adopt.AdoptObject
import com.takhyungmin.dowadog.adopt.adapter.UrgentAnimalAdapter
import com.takhyungmin.dowadog.adopt.model.get.GetAdoptPublicDetailData
import com.takhyungmin.dowadog.adopt.model.get.UrgentAnimalData
import com.takhyungmin.dowadog.dogdetail.DogDetailActivity
import com.takhyungmin.dowadog.presenter.activity.AdoptUrgentAnimalActivityPresenter
import kotlinx.android.synthetic.main.activity_urgent_animal.*

class AdoptUrgentAnimalActivity : AppCompatActivity() {

    lateinit var urgentAnimalAdapter: UrgentAnimalAdapter
    lateinit var requestManager: RequestManager
    lateinit var adoptUrgentAnimalActivityPresenter : AdoptUrgentAnimalActivityPresenter

    var currentPage = 0
    var isLoading = false
    var isLast = false
    val totalPage = 3
    val pagingCount = 16
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urgent_animal)
        init()

        setOnBinding()
    }

    fun init(){
        adoptUrgentAnimalActivityPresenter = AdoptUrgentAnimalActivityPresenter()
        adoptUrgentAnimalActivityPresenter.view = this
        flag = intent.getIntExtra("flag", 0)
        Log.v("flag", flag.toString())

        if(flag == 0){
            AdoptObject.adoptUrgentAnimalActivityPresenter = adoptUrgentAnimalActivityPresenter
            adoptUrgentAnimalActivityPresenter.requestUrgentList(currentPage, pagingCount)
        }else{
            AdoptObject.adoptUrgentAnimalActivityPresenter = adoptUrgentAnimalActivityPresenter
            adoptUrgentAnimalActivityPresenter.requestStoryAnimalList(currentPage, pagingCount)
            tv_title_urgent_ani_act.text = "제 이야기를 들어볼래요?"
        }
    }

    fun initView(urgentAnimalDatas : ArrayList<UrgentAnimalData>){
        requestManager = Glide.with(this)
        urgentAnimalAdapter = UrgentAnimalAdapter(this, urgentAnimalDatas, requestManager)
        //rv_urgent_ani_act.setFocusable(false)
        rv_urgent_ani_act.adapter = urgentAnimalAdapter
        rv_urgent_ani_act.layoutManager = GridLayoutManager(this, 2)

        if(pagingCount > urgentAnimalDatas.size){
            isLast = true
            progress_community_adopt_urgent.visibility = View.GONE
        }

        scroll_urgent_adopt_frame.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            Log.v("scroll", "scroll")
            if (scrollY == ( v.getChildAt(0).height - v.height )) {
                //scroll in bottom
                Log.v("scroll", "bottom")

                if (!isLoading and !isLast) {
                    isLoading = true
                    Log.v("scroll", currentPage.toString())
                    currentPage++
                    Handler().postDelayed(Runnable {
                        //communityFragmentPresenter.nextPage(currentPage, itemCount)
                        Log.v("scroll", "more")
                        adoptUrgentAnimalActivityPresenter.requestUrgentList(currentPage, pagingCount)
                    }, 800)
                }
            }
        })
//        rv_urgent_ani_act.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if(!rv_urgent_ani_act.canScrollVertically(1)){
//                    if (!isLoading and !isLast) {
//                        isLoading = true
//                        Log.v("scroll", currentPage.toString())
//                        currentPage++
//                        Handler().postDelayed(Runnable {
//                            //communityFragmentPresenter.nextPage(currentPage, itemCount)
//                            Log.v("scroll", "more")
//                            adoptUrgentAnimalActivityPresenter.requestUrgentList(currentPage, 10)
//                        }, 2000)
//                    }
//                }
//            }
//        })

    }

    fun loadNextPage(results : ArrayList<UrgentAnimalData>){
        Log.v("scroll", "add")
        urgentAnimalAdapter.addAll(results)
        isLoading = false
        if (pagingCount > results.size) {
            progress_community_adopt_urgent.visibility = View.GONE
            isLast = true
        }
    }

    fun setOnBinding(){
        btn_urgent_back.clicks().subscribe {
            finish()
        }
    }

    fun toDetail(datas : GetAdoptPublicDetailData){
        startActivity(Intent(this, DogDetailActivity::class.java))
    }

    fun toDetailActivity(id : Int){
//        val intent = Intent(this, DogDetailActivity::class.java)
//        intent.putExtra("animalId", id)
//        startActivity(intent)

        var intent = Intent(this@AdoptUrgentAnimalActivity,  DogDetailActivity::class.java)
        intent.putExtra("animalId", id)
        startActivityForResult(intent, 6900)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 6900){
            Log.v("check", "다녀옴")
            if(flag == 0){
                Log.v("check", "긴근 데이터 들어옴")
                adoptUrgentAnimalActivityPresenter.requestUrgentList(0, pagingCount)
                currentPage = 0
            }else{
                Log.v("check", "스토리 데이터 들어옴")
                adoptUrgentAnimalActivityPresenter.requestStoryAnimalList(0, pagingCount)
                currentPage = 0
                tv_title_urgent_ani_act.text = "제 이야기를 들어볼래요?"
            }
        }
    }
}
