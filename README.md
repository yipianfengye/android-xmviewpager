# android-xmviewpager

本文我们将介绍一个使用kotlin实现的仿照UC头条ViewPager的左右滑动效果。这个项目是为了学习kotlin的使用以及基本语法，在实现的过程中主要需要注意的有两点：一个是UC头条在滑动过程中的遮盖动画效果，一个是跨多个Tab点击屏蔽多个页面滑动效果。

**本项目的github地址：<a href="https://github.com/yipianfengye/android-xmviewpager">android-xmviewpager</a>，欢迎star和follow。**

在介绍具体的使用说明之前，我们先看一下简单的实现效果：
<br>
![image](https://github.com/yipianfengye/android-xmviewpager/blob/master/images/20160922095746542.gif)


**实现说明**

本项目是通过TabLayout+ViewPager的方式实现的，这里我们首先看一下整个页面的布局文件的实现方式：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".java.TabLayoutActivity"
    android:orientation="vertical">
    <android.support.design.widget.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">
        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:layout_scrollFlags="scroll|enterAlways"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>

        <android.support.design.widget.TabLayout
            android:id="@+id/tabs"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tabIndicatorColor="#ADBE107E"
            app:tabIndicatorHeight="0dp"
            app:tabMode="scrollable"
            app:tabPadding="0dp"
            />

    </android.support.design.widget.AppBarLayout>

    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"/>

</LinearLayout>
```

可以发现其是通过TabLayout和ViewPager的方式实现的，在实现过程中由于要求点击跨多个Tab的时候屏蔽多次滑动效果，这里重写了TabLayout的onTabSelectedListener监听：

```
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
```

然后我们可以看一下数据初始化是如何实现的：

```
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
```


这其中需要注意的是：viewPager的setCurrentItem方法，表示会将viewPager的当前显示Item设置为指定的item，而我们可以发现这里的setCurrentItem有两个参数，第一个参数，是显示当前Item的position，而第二个参数为boolean类型，表示是否有滑动效果，比如当前我们在ViewPager的第一项，而我们点击了TabLayout的第八项，这时候如果我们调用了：setCurrentItem(8, true),它表示我们将滑动到ViewPager的第八项，且有滚动效果。这样我们做一下变通，当我们点击的TabLayout与当前Item的距离大于一个Item的时候就先滑动到当前Item的前一个并且没有滑动效果，然后在执行一次setCurrentItem方法，这样在跨多个Tab点击的时候就屏蔽了多个Item滚动的效果了。


在实现过程中还需要实现滑动覆盖的效果，一开始想了很久包括使用ViewPager的setPageTransformer方法，但是还是没法实现这个思路，后来经过同事指点，终于搞定了。就是对ViewPager每一项item中的子View执行动画效果，这样就会实现需求的动画效果了。

以下是自己重写的setPageTransformer类：

```
/**
 * Created by aaron on 16/9/13.
 * 自定义实现ViewPager的切换动画效果
 */
class MTransformer : ViewPager.PageTransformer {

    /**
     * 回调方法,重写viewpager的切换动画
     */
    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val wallpaper = view.findViewById(R.id.recycler_view)
        if (position < -1) { // [-Infinity,-1)
            wallpaper.translationX = 0.toFloat()
            view.translationX = 0.toFloat()
        } else if (position <= 1) { // [-1,1]
            wallpaper.translationX = pageWidth * getFactor(position)
            view.translationX = 8 * position
        } else { // (1,+Infinity]
            wallpaper.translationX = 0.toFloat()
            view.translationX = 0.toFloat()
        }
    }

    private fun getFactor(position: Float): Float {
        return -position / 2
    }

}
```
可以看到在我们自定义的PageTransformer中，我们通过findViewById方法获取了滑动Item的子View，并对子View执行translationX操作，进而实现了滑动Item的遮盖效果。

另外由于本文主要介绍Kotlin的使用，更多关于Kotlin的相关知识点，可参考：

<a href="https://kotlinlang.org/docs/reference/basic-syntax.html">Basic Syntax - Kotlin Programing</a>

<a href="http://www.infoq.com/cn/news/2015/06/Android-JVM-JetBrains-Kotlin">Kotlin：Android事件的Swift</a>

<a href="http://www.jianshu.com/p/a7fadc79e0fb">Kotlin在Android工程中的应用</a>

当然更具体的关于本控件的实现可以下载源码参考。

**总结：**

以上就是通过Kotlin实现的仿照UC头条ViewPager左右滑动效果的小项目。当然现在还很不完善，对于源码有兴趣的同学可以到github上看一下具体实现。项目地址：<a href="https://github.com/yipianfengye/android-xmviewpager">android-xmviewpager</a>


<br>另外对github项目，开源项目解析感兴趣的同学可以参考我的：
<br><a href="http://blog.csdn.net/qq_23547831/article/details/50010419">Github项目解析（一）-->上传Android项目至github</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/50017151">Github项目解析（二）-->将Android项目发布至JCenter代码库 </a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/50536690">Github项目解析（三）-->Android内存泄露监测之leakcanary</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/50592352"> Github项目解析（四）-->动态更改TextView的字体大小</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/51707796"> Github项目解析（五）-->Android日志框架</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/51713824"> Github项目解析（六）-->自定义实现ButterKnife框架</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/51730472">Github项目解析（七）-->防止按钮重复点击</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/51764304">Github项目解析（八）-->Activity启动过程中获取组件宽高的五种方式</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/51821159">Github项目解析（九）-->实现Activity跳转动画的五种方式</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/52037710">Github项目解析（十）-->几行代码快速集成二维码扫描库</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/52121633">Github项目解析（十一）-->一个简单，强大的自定义广告活动弹窗</a>
<br><a href="http://blog.csdn.net/qq_23547831/article/details/52593674"> Github项目解析（十二）-->一个简单的多行文本显示控件</a>
