package com.curiositymeetsminds.doreminder

import android.provider.BaseColumns

/**
 * Object to provide table name, column names and other important constant and functions
 * to access the task table
 */

object TasksContract {
    internal const val TABLE_NAME = "Tasks"

    object Columns {
        const val TASK_ID = BaseColumns._ID
        const val TASK_NAME = "Name"
        const val TASK_DESCRIPTION = "Description"
        const val TASK_TYPE = "TaskType"
    }
}