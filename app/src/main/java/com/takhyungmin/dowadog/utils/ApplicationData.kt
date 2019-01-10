package com.takhyungmin.dowadog.utils

import android.content.Context

object ApplicationData {

    var baseUrl = "http://waitforudog.ml:8080/"

    var auth = ""

    var loginState = false

    lateinit var applicationContext : Context

    var firstLoginFlag = false

    var userName = ""

    var userBirth = ""

    var userPhone = ""

    var userImage = ""

    var userEmail = ""
}