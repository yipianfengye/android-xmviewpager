package com.example.xiaomi.mytablayout

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mich.android_xmviewpager.R

/**
 * Created by aaron on 16/9/14.
 *
 */
class MRecyclerViewAdapter(val mContext : Context, val content : String) : RecyclerView.Adapter<MRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.list_item_card_main, parent, false)
        val textview = view.findViewById(R.id.textview) as TextView
        textview.text = content

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MRecyclerViewAdapter.ViewHolder?, position: Int) {
        val view = holder!!.mView
        view.setOnClickListener {
            // val intent = Intent(mContext, SecondActivity::class.java)
            // mContext.startActivity(intent)
        }
    }

    class ViewHolder(val mView : View) : RecyclerView.ViewHolder(mView)
}