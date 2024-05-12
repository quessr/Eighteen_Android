package com.eighteen.eighteenandroid.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.eighteen.eighteenandroid.R

/**
 *
 * @file ViewPagerAdapter.kt
 * @date 05/08/2024
 */

class ViewPagerAdapter (
    private val context: Context,
    private val tagList: List<String>,
) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, pos: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater!!.inflate(R.layout.today_teen_item, container, false)
        val profileImg: ImageView = view.findViewById(R.id.img_today_teen)
        Glide.with(context).load(tagList[pos]).into(profileImg);

        container.addView(view, pos)
        return view
    }

    override fun getCount(): Int {
        return tagList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

}