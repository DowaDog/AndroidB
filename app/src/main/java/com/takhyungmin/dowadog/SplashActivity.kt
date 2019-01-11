package com.takhyungmin.dowadog

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.takhyungmin.dowadog.home.activity.HomeActivity
import com.takhyungmin.dowadog.login.LoginActivity
import com.takhyungmin.dowadog.utils.ApplicationData
import com.takhyungmin.dowadog.utils.GifDrawableImageViewTarget
import com.takhyungmin.dowadog.utils.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ApplicationData.applicationContext = applicationContext

        Glide.with(this).load(R.drawable.splash).into(GifDrawableImageViewTarget(splash_image,1))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel("waitforudog", "기다릴개", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "기다릴개"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#ffc233")
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 100, 200)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(notificationChannel)
        }



        val handler = Handler()
        handler.postDelayed(Runnable {
            if(SharedPreferenceController.getAccessToken(this).isEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("splash", 1)
                startActivity(intent)
            }else{
                val intent = Intent(this, HomeActivity::class.java)
                ApplicationData.auth = SharedPreferenceController.getRefreshToken(this)
                ApplicationData.loginState = true
                startActivity(intent)
            }
            //startActivity<LoginActivity>()
            finish()
        }, 3000)


    }

}