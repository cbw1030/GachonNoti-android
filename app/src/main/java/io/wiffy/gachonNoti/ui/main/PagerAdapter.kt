package io.wiffy.gachonNoti.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(
    fm: FragmentManager,
    private var lists: ArrayList<Fragment>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment=lists[position]
    override fun getCount(): Int =lists.size
}