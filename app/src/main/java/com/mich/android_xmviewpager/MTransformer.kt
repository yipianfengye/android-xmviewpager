package com.example.xiaomi.mytablayout

import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import com.mich.android_xmviewpager.R

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