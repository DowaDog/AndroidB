package com.takhyungmin.dowadog.adoptedanimal.model.get

data class Data(
        val age: String,
        val gender: String,
        val id: Int,
        val inoculationArray: Any,
        val kind: String,
        val name: String,
        val neuterYn: Boolean,
        val profileImg: String,
        val weight: String,
        val animalType : String
)