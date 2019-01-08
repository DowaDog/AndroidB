package com.takhyungmin.dowadog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.takhyungmin.dowadog.home.activity.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //startActivity(Intent(this, HomeActivity::class.java))
        //requestReadExternalStoragePermission()
    }

    private fun requestReadExternalStoragePermission(){
        //첫번째 if문을 통해 과거에 이미 권한 메시지에 대한 OK를 했는지 아닌지에 대해 물어봅니다!
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1000)
                val intent2 = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "01036439714"))
                startActivityForResult(intent2, 2009)
            } else {
                //아래 코드는 권한을 요청하는 메시지를 띄우는 기능을 합니다! 요청에 대한 결과는 callback으로 onRequestPermissionsResult 메소드에서 받습니다!!!
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1000)
                Log.v("ygyg", "ygyg2")
            }
        } else {
            val intent2 = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "01036439714"))
            startActivityForResult(intent2, 2009)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2009)
            startActivity(Intent(this, HomeActivity::class.java))
    }



}
