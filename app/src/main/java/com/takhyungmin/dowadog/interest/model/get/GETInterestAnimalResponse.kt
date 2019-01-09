package com.takhyungmin.dowadog.interest.model.get

data class GETInterestAnimalResponse(
        val data: ArrayList<Data>,
        val message: String,
        val status: Int
)