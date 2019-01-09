package com.takhyungmin.dowadog.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log

class HomeDialogFragmentStatePagerAdapter(fm: FragmentManager, val fragmentCount: Int) : FragmentStatePagerAdapter(fm) {
    init {
        Log.v("adapter", "adapter2")
    }
    override fun getItem(position: Int): Fragment? {
        Log.v("adapter", "adapter3")

        when (position) {
            0 -> return HomeDialogFirstFragment()
            1 -> return HomeDialogRealSecondFragment()
            2 -> return HomeDialogSecondFragment()
            3 -> return HomeDialogThirdFragment()
            4 -> return HomeDialogFourthFragment()
            5 -> return HomeDialogFifthFragment()
            else -> return null
        }
    }

    override fun getCount(): Int = fragmentCount
}