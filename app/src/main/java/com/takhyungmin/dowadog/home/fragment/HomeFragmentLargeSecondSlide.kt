package com.takhyungmin.dowadog.home.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.presenter.fragment.HomeFragmentLargeSecondSlidePresenter
import com.takhyungmin.dowadog.utils.CustomTypeSpan
import kotlinx.android.synthetic.main.fragment_home_large_second_slide.*

class HomeFragmentLargeSecondSlide : Fragment(){

    lateinit var homeFragmentLargeSecondSlidePresenter: HomeFragmentLargeSecondSlidePresenter
    var currentPage = 0

    val DELAY_MS: Long = 500//delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_large_second_slide, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragmentLargeSecondSlidePresenter = HomeFragmentLargeSecondSlidePresenter()
        homeFragmentLargeSecondSlidePresenter.view = this
    }


    override fun onStart() {
        super.onStart()
        homeFragmentLargeSecondSlidePresenter.init()
    }


    fun init(){

        val font1 = Typeface.createFromAsset(activity!!.assets, "nanum_square_extra_bold.ttf")
        val font2 = Typeface.createFromAsset(activity!!.assets, "nanum_square_light.ttf")

        val ssb = SpannableStringBuilder("입양이\n승인되지 않았습니다.")
        ssb.setSpan(CustomTypeSpan("", font1), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        ssb.setSpan(CustomTypeSpan("", font2), 2, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        ssb.setSpan(CustomTypeSpan("", font1), 3, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        ssb.setSpan(CustomTypeSpan("", font2), 5, "입양이\n승인되지 않았습니다.".length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        tv_home_fragment_slide_text.text = ssb


//        val handler = Handler()
//        val Update = Runnable {
//            if (currentPage == 4) {
//                currentPage = 0
//            }
//            vp_home_fragment_second_slide_contents.setCurrentItem(currentPage++, true)
//        }
//
//        val timer = Timer() // This will create a new Thread
//        timer.schedule(object : TimerTask() { // task to be scheduled
//
//            override fun run() {
//                handler.post(Update)
//            }
//        }, DELAY_MS, PERIOD_MS)
    }



    fun changeIndicator(position : Int){
    }
}