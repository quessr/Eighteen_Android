package com.eighteen.eighteenandroid.presentation.myprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemMyProfileIntroduceBinding
import com.eighteen.eighteenandroid.databinding.ItemMyProfileLinkBinding
import com.eighteen.eighteenandroid.databinding.ItemMyProfileProfileBinding
import com.eighteen.eighteenandroid.databinding.ItemMyProfileTenOfQnaBinding
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem
import com.eighteen.eighteenandroid.presentation.myprofile.viewholder.BaseMyProfileViewHolder
import com.eighteen.eighteenandroid.presentation.myprofile.viewholder.MyProfileIntroduceViewHolder
import com.eighteen.eighteenandroid.presentation.myprofile.viewholder.MyProfileLinkViewHolder
import com.eighteen.eighteenandroid.presentation.myprofile.viewholder.MyProfileProfileViewHolder
import com.eighteen.eighteenandroid.presentation.myprofile.viewholder.MyProfileTenOfQnaViewHolder

class MyProfileAdapter(private val clickListener: MyProfileClickListener) :
    ListAdapter<MyProfileItem, BaseMyProfileViewHolder<out MyProfileItem, out ViewBinding>>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseMyProfileViewHolder<out MyProfileItem, out ViewBinding> {
        fun <VB : ViewBinding> inflateBinding(bindingInflate: (LayoutInflater, ViewGroup, Boolean) -> VB) =
            bindingInflate.invoke(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            ITEM_TYPE_PROFILE -> MyProfileProfileViewHolder(
                clickListener,
                inflateBinding(ItemMyProfileProfileBinding::inflate)
            )

            ITEM_TYPE_LINK -> MyProfileLinkViewHolder(
                clickListener,
                inflateBinding(ItemMyProfileLinkBinding::inflate)
            )

            ITEM_TYPE_INTRODUCE -> MyProfileIntroduceViewHolder(
                clickListener,
                inflateBinding(ItemMyProfileIntroduceBinding::inflate)
            )
            else -> MyProfileTenOfQnaViewHolder(
                clickListener,
                inflateBinding(ItemMyProfileTenOfQnaBinding::inflate)
            )
        }
    }

    override fun onBindViewHolder(
        holder: BaseMyProfileViewHolder<out MyProfileItem, out ViewBinding>,
        position: Int
    ) {
        when (val item = getItem(position)) {
            is MyProfileItem.Profile -> (holder as? MyProfileProfileViewHolder)?.onBind(model = item)
            is MyProfileItem.Link -> (holder as? MyProfileLinkViewHolder)?.onBind(model = item)
            is MyProfileItem.Introduce -> (holder as? MyProfileIntroduceViewHolder)?.onBind(model = item)
            is MyProfileItem.TenOfQna -> (holder as? MyProfileTenOfQnaViewHolder)?.onBind(model = item)
        }
    }


    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is MyProfileItem.Profile -> ITEM_TYPE_PROFILE
            is MyProfileItem.Link -> ITEM_TYPE_LINK
            is MyProfileItem.Introduce -> ITEM_TYPE_INTRODUCE
            is MyProfileItem.TenOfQna -> ITEM_TYPE_TEN_OF_QNA
        }


    companion object {
        private const val ITEM_TYPE_PROFILE = 1
        private const val ITEM_TYPE_LINK = 2
        private const val ITEM_TYPE_INTRODUCE = 3
        private const val ITEM_TYPE_TEN_OF_QNA = 4
        private val diffUtil = object : DiffUtil.ItemCallback<MyProfileItem>() {
            override fun areItemsTheSame(oldItem: MyProfileItem, newItem: MyProfileItem): Boolean =
                oldItem.areItemsTheSame(newItem)

            override fun areContentsTheSame(
                oldItem: MyProfileItem,
                newItem: MyProfileItem
            ): Boolean = oldItem.areContentsTheSame(newItem)
        }
    }
}