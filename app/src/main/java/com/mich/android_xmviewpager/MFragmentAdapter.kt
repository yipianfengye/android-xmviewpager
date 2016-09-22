package com.example.xiaomi.mytablayout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by aaron on 16/9/13.
 * 创建FragmentAdapter
 */
class MFragmentAdapter(fm : FragmentManager, val mFragments : List<Fragment>, val mTitles : List<String>) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {

        return mFragments[position]
    }

    override fun getCount(): Int {

        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}