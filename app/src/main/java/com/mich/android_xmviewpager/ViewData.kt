package com.example.xiaomi.mytablayout

import java.util.*

/**
 * Created by aaron on 16/9/14.
 * 主要用于保存界面ViewPager数据
 */
class ViewData {

    /**
     * 该方法用于获取ViewPager TAB 显示数据
     */
    fun getTitles() : ArrayList<String> {
        /**
         * 通过类名创建该类的对象,这里直接调用java中的集合框架
         */
        val titles = ArrayList<String>()

        titles.clear()
        titles.add("推荐")
        titles.add("视频")
        titles.add("热点")
        titles.add("娱乐")
        titles.add("体育")
        titles.add("北京")
        titles.add("财经")
        titles.add("科技")
        titles.add("汽车")
        titles.add("社会")
        titles.add("搞笑")
        titles.add("军事")
        titles.add("历史")
        titles.add("涨知识")
        titles.add("NBA")
        titles.add("两性")

        return titles
    }

}