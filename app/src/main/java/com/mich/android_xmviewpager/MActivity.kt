package com.mich.android_xmviewpager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.example.xiaomi.mytablayout.MFragmentAdapter
import com.example.xiaomi.mytablayout.MListFragment
import com.example.xiaomi.mytablayout.MTransformer
import com.example.xiaomi.mytablayout.ViewData
import java.util.*

class MActivity : AppCompatActivity() {
    /**
     * 用于定义常量
     */
    companion object {
        /**
         * 主要用于定义Log TAG
         */
        val TAG = MActivity.javaClass.simpleName
    }

    /**
     * 通过var定义变量,var表示变量既可读也可写
     * 通过val定义变量,val表示变量只是可读
     */
    var isOk = true
    var mTabLayout : TabLayout ?= null
    var mViewPager : ViewPager ?= null

    /**
     * override 表示函数重载父类方法
     * ? 表示变量可为空
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m)

        initViewPager()
    }

    /**
     * 自定义函数, : Unit 表示函数没有返回值
     */
    fun initViewPager() : Unit {
        /**
         * 获取初始化数据
         */
        val titles = ViewData().getTitles()

        /**
         * as 类似于java中的类型强转
         */
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        mViewPager = findViewById(R.id.viewpager) as ViewPager
        mTabLayout = findViewById(R.id.tabs) as TabLayout

        /**
         * 通过 in 关键字实现循环遍历
         * 在调用mTabLayou变量的方法时,由于mTabLayout可能为空,所以在调用方法时添加!!
         * titles[] 与 titles.get 方法的功能是一样的
         * titles.indices 获取的是数组的下标
         */
        for (i in titles.indices) {
            mTabLayout!!.addTab(mTabLayout!!.newTab().setText(titles[i]))
        }

        val fragments = ArrayList<Fragment>()

        /**
         * 循环遍历添加ViewPager的Fragment
         */
        for (i in titles.indices) {
            val listFragment = MListFragment()
            val bundle = Bundle()
            val sb = StringBuffer()
            for (j in 1..8) {
                sb.append(titles[i]).append(" ")
            }
            bundle.putString("content", sb.toString())
            listFragment.arguments = bundle
            fragments.add(listFragment)
        }

        val mFragmentAdapteradapter = MFragmentAdapter(supportFragmentManager, fragments, titles)
        mViewPager!!.adapter = mFragmentAdapteradapter
        mViewPager!!.adapter = mFragmentAdapteradapter
        mTabLayout!!.setupWithViewPager(mViewPager)
        mTabLayout!!.setTabsFromPagerAdapter(mFragmentAdapteradapter)

        /**
         * 自定义设置ViewPager切换动画
         */
        mViewPager!!.setPageTransformer(true, MTransformer())

        /**
         * 通过object : TabLayout.OnTabSelectedListener 的方式创建内部匿名类(这里主要是接口)
         */
        mTabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                /**
                 * 控制变量
                 */
                if (isOk) {
                    isOk = false
                    val currentItemIndex = mViewPager!!.currentItem

                    if (Math.abs(currentItemIndex - tab!!.position) > 1) {
                        /**
                         * 向后点击
                         */
                        if (currentItemIndex <= tab!!.position) {
                            mViewPager!!.setCurrentItem(tab.position - 1, false)
                            mViewPager!!.setCurrentItem(tab.position, true)
                        }
                        /**
                         * 向前点击
                         */
                        else {
                            mViewPager!!.setCurrentItem(tab.position + 1, false)
                            mViewPager!!.setCurrentItem(tab.position, true)
                        }
                    } else {
                        mViewPager!!.setCurrentItem(tab.position, true)
                    }

                    isOk = true
                }
            }
        })

    }
}
