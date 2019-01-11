package com.takhyungmin.dowadog.home.model.get

data class GetFragmentData (
    var login : Boolean,
    var userCheck : Boolean,
    var place : String?,
    var time : String?,
    var material : String?,
    var view : String,
    var totalCount : Int
)
