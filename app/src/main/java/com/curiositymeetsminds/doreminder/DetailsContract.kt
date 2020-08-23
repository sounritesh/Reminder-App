package com.curiositymeetsminds.doreminder

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object DetailsContract {

    internal const val TABLE_NAME = "Details"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"

    object Columns {
        const val DETAIL_ID = BaseColumns._ID
        const val DETAIL_MOBILE_NUMBER = "MobileNumber"
        const val DETAIL_EMAIL_ID = "EmailId"
        const val DETAIL_SUBJECT = "Subject"
        const val DETAIL_BODY = "Body"
        const val DETAIL_SEARCH_QUERY = "SearchQuery"
    }

    fun getId (uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId (id: Long) : Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}