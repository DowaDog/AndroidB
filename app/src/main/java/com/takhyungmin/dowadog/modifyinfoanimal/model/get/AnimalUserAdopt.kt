package com.takhyungmin.dowadog.modifyinfoanimal.model.get

data class AnimalUserAdopt(
        val age: String,
        val animalType: String,
        val gender: String,
        val id: Int,
        val kind: String,
        val name: String,
        val neuterYn: Boolean,
        val profileImg: String,
        val weight: String
)