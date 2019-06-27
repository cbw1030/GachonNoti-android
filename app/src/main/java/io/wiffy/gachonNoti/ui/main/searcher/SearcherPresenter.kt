package io.wiffy.gachonNoti.ui.main.searcher

import io.wiffy.gachonNoti.ui.main.MainContract

class SearcherPresenter(val mView:MainContract.FragmentSearcher):MainContract.PresenterSearcher {
    override fun initPresent() {
        mView.changeUI()
    }
}