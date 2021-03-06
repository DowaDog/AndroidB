package com.takhyungmin.dowadog.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.takhyungmin.dowadog.R
import kotlinx.android.synthetic.main.custom_dialog_share.*

class CustomShareDogDialog (context: Context,
                            private val mFirstClickListener: View.OnClickListener?,
                            private val mSecondClickListener: View.OnClickListener?,
                            private val mLeftClickListener: View.OnClickListener?,
                            private val mRightClickListener: View.OnClickListener?, private val leftText: String, private val rightText: String) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    private val clickedState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.55f
        window!!.attributes = lpWindow

        setContentView(R.layout.custom_dialog_share)

        tv_left_share_custom_dialog_community_detail.text = leftText
        tv_right_share_custom_dialog_community_detail.text = rightText

        if (mLeftClickListener != null && mRightClickListener != null) {
            btn_kakao_share_custom_dialog_community_detail.setOnClickListener(mFirstClickListener)
            btn_link_share_custom_dialog_community_detail.setOnClickListener(mSecondClickListener)
            btn_left_share_custom_dialog_community_detail.setOnClickListener(mLeftClickListener)
            btn_right_share_custom_dialog_community_detail.setOnClickListener(mRightClickListener)
        } else if (mLeftClickListener != null && mRightClickListener == null) {
            btn_left_share_custom_dialog_community_detail.setOnClickListener(mLeftClickListener)
        } else {

        }
    }

}