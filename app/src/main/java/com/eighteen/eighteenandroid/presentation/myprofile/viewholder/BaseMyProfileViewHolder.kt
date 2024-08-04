package com.eighteen.eighteenandroid.presentation.myprofile.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem

abstract class BaseMyProfileViewHolder<T : MyProfileItem, VB : ViewBinding>(binding: VB) :
    ViewHolder(binding.root) {

    protected val context: Context = binding.root.context
    open fun onBind(model: T) {}
}