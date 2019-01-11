package com.takhyungmin.dowadog.searchresult

import com.takhyungmin.dowadog.presenter.activity.SearchEditTextActivityPresenter
import com.takhyungmin.dowadog.presenter.activity.SearchKeywordResultActivityPresenter
import com.takhyungmin.dowadog.presenter.activity.SearchResultActivityPresenter

object SearchResultObject {
    lateinit var searchResultActivityPresenter: SearchResultActivityPresenter
    lateinit var searchKeywordResultActivityPresenter: SearchKeywordResultActivityPresenter
    lateinit var searchEditTextActivityPresenter : SearchEditTextActivityPresenter
}