package com.eighteen.eighteenandroid.presentation.common

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.util.Locale


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
