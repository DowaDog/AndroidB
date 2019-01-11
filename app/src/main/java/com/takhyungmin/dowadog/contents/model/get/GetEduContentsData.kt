package com.takhyungmin.dowadog.contents.model.get

data class GetEduContentsData (
        var content : ArrayList<GetEduContentsContents>,
        var edu : GetEduContentsEdu,
        var cardnewsThumbnail : String
)