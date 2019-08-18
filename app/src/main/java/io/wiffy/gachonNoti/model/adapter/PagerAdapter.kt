package io.wiffy.gachonNoti.model.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import io.wiffy.gachonNoti.model.SuperContract

class PagerAdapter(
    fm: FragmentManager?,
    private var lists: ArrayList<Fragment?>
) : FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), SuperContract.SuperObject {
    override fun getItem(position: Int): Fragment = lists[position]!!
    override fun getCount(): Int = lists.size
}