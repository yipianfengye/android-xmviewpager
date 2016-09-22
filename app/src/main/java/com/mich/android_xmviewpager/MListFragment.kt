package com.example.xiaomi.mytablayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mich.android_xmviewpager.R

/**
 * Created by aaron on 16/9/13.
 * 继承Fragment,需要添加小括号,若为实现某一个接口,则不需要添加小括号
 */
class MListFragment : Fragment() {

    var mRecyclerView : RecyclerView ?= null
    var mRecyclerViewAdapter : MRecyclerViewAdapter ?= null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var rootView  = inflater!!.inflate(R.layout.list_fragment, container, false)
        mRecyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(mRecyclerView!!.context)
        mRecyclerViewAdapter = MRecyclerViewAdapter(activity, arguments.getString("content"))
        mRecyclerView!!.adapter = mRecyclerViewAdapter

        return rootView
    }

    override fun onResume() {
        super.onResume()

        mRecyclerViewAdapter!!.notifyDataSetChanged()
    }
}