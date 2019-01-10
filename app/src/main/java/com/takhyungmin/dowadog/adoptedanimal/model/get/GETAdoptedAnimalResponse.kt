package com.takhyungmin.dowadog.adoptedanimal.model.get

data class GETAdoptedAnimalResponse(
        val data: ArrayList<Data>,
        val message: String,
        val status: Int
)