package com.takhyungmin.dowadog.home.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.adopt.fragment.AdoptAnimalFindFragment
import com.takhyungmin.dowadog.home.HomeObject
import com.takhyungmin.dowadog.presenter.fragment.HomeFragmentPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_slide_up.*


class HomeFragment : Fragment() {
    lateinit var slideUp : SlideUp
    lateinit var homeFragmentPresneter : HomeFragmentPresenter
    var state  : String = "NO"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragmentPresneter = HomeFragmentPresenter()
        homeFragmentPresneter.view = this
        HomeObject.homeFragmentPresenter = homeFragmentPresneter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentPresneter.init()
        init()
        setBinding()
    }


    override fun onStart() {
        super.onStart()

    }

    fun addFragment(fragment : Fragment){
        val fm = activity!!.supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.frame_fragment_slide_contents, fragment)
        transaction.commitNow()
    }

    fun replaceFragment(fragment : Fragment){
        val fm = activity!!.supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        transaction.replace(R.id.frame_fragment_slide_contents, fragment)
        transaction.commitNow()
    }

    fun init(){

        slideUp = SlideUpBuilder(layout_home_fragment_slide_pannel)
                .withListeners(object : SlideUp.Listener.Events {
                    override fun onSlide(percent: Float) {
                        //layout_home_fragment_dim.alpha = 1 - percent / 100
                        HomeObject.homeActivityPresenter.adjustDim((1 - percent/100))
                        content_slide_up_view.alpha = 1 - percent/100
                        if (btn_home_fragment_slide.isShown && percent < 100) {
                            btn_home_fragment_slide.visibility = View.GONE
                        }
                    }

                    override fun onVisibilityChanged(visibility: Int) {
                        if (visibility == View.GONE) {
                            homeFragmentPresneter.init()
                            btn_home_fragment_slide.visibility = View.VISIBLE
                        }

                        if(visibility == View.VISIBLE){
                            //TODO : 홈 통신
                            homeFragmentPresneter.postHomeRead()
                        }
                    }
                })
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .withSlideFromOtherView(btn_home_fragment_slide)
                .build()
    }

    fun setBinding(){
        btn_home_fragment_slide.clicks().subscribe {
            slideUp.show()
        }

        btn_home_fragment_new.clicks().subscribe {
            HomeObject.homeActivityPresenter.replaceFragment(AdoptAnimalFindFragment())
            HomeObject.homeActivityPresenter.homeBtnClick()
            HomeObject.homeActivityPresenter.toNew()
        }
    }

    fun checkChange(check : Boolean){
        if(check)
            img_home_fragment_check.visibility = View.GONE
        else
            img_home_fragment_check.visibility = View.VISIBLE
    }

    fun setCount(count : Int){
        tv_home_fragment_count.text = count.toString()
    }

    fun swipeEnable(position : Int){

    }
}
