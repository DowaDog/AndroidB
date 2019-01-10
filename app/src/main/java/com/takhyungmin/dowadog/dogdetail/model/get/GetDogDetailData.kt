package com.takhyungmin.dowadog.dogdetail.model.get

data class GetDogDetailData(
        val age: String,
        val animalStoryListAos: ArrayList<String>,
        val careName: String,
        val careTel: String,
        val educationState: Boolean,
        val happenPlace: String,
        val id: Int,
        val kindCd: String,
        val liked: Boolean,
        val noticeEddt: String,
        val noticeStdt: String,
        val processState: String,
        val region: String,
        val remainDateState: Boolean,
        val sexCd: String,
        val specialMark: String,
        val thumbnailImg: String,
        val type: String,
        val weight: String
)