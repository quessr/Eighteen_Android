package com.eighteen.eighteenandroid.presentation.common

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.File
import java.util.Locale

//https://stackoverflow.com/questions/8589645/how-to-determine-mime-type-of-file-in-android/31691791#31691791
fun getMimeTypeFromUri(uri: Uri, context: Context): String? =
    if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
        val cr: ContentResolver = context.contentResolver
        cr.getType(uri)
    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
            uri.toString()
        )
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.lowercase(Locale.getDefault())
        )
    }

fun getVideoThumbnailFromUri(context: Context, uri: Uri, time: Long): Bitmap? =
    getVideoThumbnailFromUri(context = context, uri = uri, times = listOf(time)).firstOrNull()


fun getVideoThumbnailFromUri(context: Context, uri: Uri, times: List<Long>): List<Bitmap?> {
    val retriever = MediaMetadataRetriever()
    val thumbnails = mutableListOf<Bitmap?>()
    try {
        retriever.setDataSource(context, uri)
        times.forEach { time ->
            val thumbnail = retriever.getFrameAtTime(time)
            thumbnails.add(thumbnail)
        }
    } catch (e: Throwable) {
        Log.e("getVideoThumbnailFromUri", "${e.message}")
    } finally {
        retriever.release()
    }
    return thumbnails
}

fun contentUriToPath(context: Context, contentUri: Uri): String? {
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
    return cursor?.run {
        val idx = getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor.moveToFirst()
        val path = getString(idx)
        close()
        path
    }
}

fun getFileExtension(context: Context, uri: Uri): String {
    val extension = if (uri.scheme != null && uri.scheme == ContentResolver.SCHEME_CONTENT) {
        val mime = MimeTypeMap.getSingleton()
        mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
    } else MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(uri.path?.let { File(it) }).toString())
    return if (extension.isNullOrEmpty()) ".mp4" else extension
}