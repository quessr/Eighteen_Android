package com.eighteen.eighteenandroid.presentation.common

import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * ViewPager2에서 recyclerView 직접 접근 불가능해서 자식 뷰들 탐색하여 RecyclerView를 반환하는 함수
 */
fun ViewPager2.getRecyclerViewOrNull() = children.find { it is RecyclerView } as? RecyclerView