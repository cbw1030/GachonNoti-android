package io.wiffy.gachonNoti.ui.main.searcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainContract

class SearcherFragment:Fragment(),MainContract.FragmentSearcher {
    lateinit var myView: View
    lateinit var mPresenter:SearcherPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_searcher, container, false)
        mPresenter = SearcherPresenter(this)
        mPresenter.initPresent()
        return myView
    }

    override fun changeUI() {

    }
}