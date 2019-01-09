package com.takhyungmin.dowadog.home.model.get

data class GetFragmentResponse (
        var status : Int,
        var message : String,
        var data : GetFragmentData
)