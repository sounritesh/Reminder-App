package com.curiositymeetsminds.doreminder

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

/**
 * Object to provide table name, column names and other important constant and functions
 * to access the task table
 */

object TasksContract {
    internal const val TABLE_NAME = "Tasks"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"

    object Columns {
        const val TASK_ID = BaseColumns._ID
        const val TASK_NAME = "Name"
        const val TASK_DESCRIPTION = "Description"
        const val TASK_TYPE = "TaskType"
    }

    fun getId (uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId (id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}