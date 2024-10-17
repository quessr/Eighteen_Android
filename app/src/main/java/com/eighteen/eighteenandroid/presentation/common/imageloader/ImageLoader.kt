package com.eighteen.eighteenandroid.presentation.common.imageloader

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

interface ImageLoader {
    fun loadUrl(imageView: ImageView, url: String?, placeHolderRes: Int? = null)
    fun loadUrl(imageView: ImageView, url: Uri?, placeHolderRes: Int? = null)
    fun loadBitmap(imageView: ImageView, bitmap: Bitmap?, placeHolderRes: Int? = null)
    fun loadUrlCenterCrop(imageView: ImageView, url: String?, placeHolderRes: Int? = null)
    fun loadUrlCenterCrop(imageView: ImageView, url: Uri?, placeHolderRes: Int? = null)
    fun loadBitmapCenterCrop(imageView: ImageView, bitmap: Bitmap?, placeHolderRes: Int? = null)
    fun loadUrlCircleCrop(imageView: ImageView, url: String?, placeHolderRes: Int? = null)

    companion object {
        private val imageLoaderImpl = GlideImageLoader()
        fun get(): ImageLoader = imageLoaderImpl
    }

    private class GlideImageLoader : ImageLoader {

        private fun loadUrlBase(imageView: ImageView, url: String?, placeHolderRes: Int?) =
            Glide.with(imageView).load(url).run {
                placeHolderRes?.let {
                    placeholder(it)
                } ?: this
            }


        private fun loadBitmapBase(imageView: ImageView, bitmap: Bitmap?, placeHolderRes: Int?) =
            Glide.with(imageView).load(bitmap).run {
                placeHolderRes?.let {
                    placeholder(it)
                } ?: this
            }

        override fun loadUrl(imageView: ImageView, url: String?, placeHolderRes: Int?) {
            loadUrlBase(imageView, url, placeHolderRes).into(imageView)
        }

        override fun loadUrl(imageView: ImageView, url: Uri?, placeHolderRes: Int?) {
            loadUrl(imageView, url.toString(), placeHolderRes)
        }

        override fun loadBitmap(
            imageView: ImageView,
            bitmap: Bitmap?,
            placeHolderRes: Int?
        ) {
            loadBitmapBase(imageView, bitmap, placeHolderRes).into(imageView)
        }

        override fun loadUrlCenterCrop(
            imageView: ImageView,
            url: String?,
            placeHolderRes: Int?
        ) {
            loadUrlBase(imageView, url, placeHolderRes).centerCrop().into(imageView)
        }

        override fun loadUrlCenterCrop(
            imageView: ImageView,
            url: Uri?,
            placeHolderRes: Int?
        ) {
            loadUrlCenterCrop(
                imageView = imageView,
                url = url.toString(),
                placeHolderRes = placeHolderRes
            )
        }

        override fun loadBitmapCenterCrop(
            imageView: ImageView,
            bitmap: Bitmap?,
            placeHolderRes: Int?
        ) {
            loadBitmapBase(imageView, bitmap, placeHolderRes).centerCrop().into(imageView)
        }

        override fun loadUrlCircleCrop(imageView: ImageView, url: String?, placeHolderRes: Int?) {
            loadUrlBase(imageView, url, placeHolderRes).circleCrop().into(imageView)
        }
    }
}