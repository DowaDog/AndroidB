package com.takhyungmin.dowadog.home.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.home.HomeObject
import kotlinx.android.synthetic.main.fragment_home_third_slide.*

class HomeFragmentThirdSlide: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_third_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_home_slide_third_place.text = HomeObject.place
        tv_home_slide_third_date.text = HomeObject.date
    }
}