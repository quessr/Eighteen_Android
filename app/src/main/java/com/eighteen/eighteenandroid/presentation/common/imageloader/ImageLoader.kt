package com.eighteen.eighteenandroid.presentation.common.imageloader

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

interface ImageLoader {
    fun loadUrl(imageView: ImageView, url: String?, placeHolderRes: Int? = null)
    fun loadUrl(imageView: ImageView, url: Uri?, placeHolderRes: Int? = null)

    companion object {
        private val imageLoaderImpl = GlideImageLoader()
        fun get(): ImageLoader = imageLoaderImpl
    }

    private class GlideImageLoader : ImageLoader {
        override fun loadUrl(imageView: ImageView, url: String?, placeHolderRes: Int?) {
            Glide.with(imageView).load(url).run {
                placeHolderRes?.let {
                    placeholder(it)
                } ?: this
            }.into(imageView)
        }

        override fun loadUrl(imageView: ImageView, url: Uri?, placeHolderRes: Int?) {
            loadUrl(imageView, url.toString(), placeHolderRes)
        }
    }
}