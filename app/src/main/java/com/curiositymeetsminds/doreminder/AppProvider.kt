package com.curiositymeetsminds.doreminder

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log

private const val TAG = "AppProvider"

const val CONTENT_AUTHORITY = "com.curiositymeetsminds.doreminder.provider"

private const val TASKS = 100
private const val TASK_ID = 101

private const val DETAILS = 200
private const val DETAILS_ID = 201

val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

class AppProvider: ContentProvider() {

    private val uriMatcher by lazy { buildUriMatcher() }
    private fun buildUriMatcher(): UriMatcher {

        Log.d(TAG, "buildUriMatcher: starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS)
        matcher.addURI(CONTENT_AUTHORITY, "${TasksContract.TABLE_NAME}/#", TASK_ID)
        matcher.addURI(CONTENT_AUTHORITY, DetailsContract.TABLE_NAME, DETAILS)
        matcher.addURI(CONTENT_AUTHORITY, "${DetailsContract.TABLE_NAME}/#", DETAILS_ID)

        return matcher
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate: starts")
        return true
    }

    override fun getType(uri: Uri): String? {
        Log.d(TAG, "getType: starts with URI $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "getType: match is $match")

        return when (match) {
            TASKS -> TasksContract.CONTENT_TYPE

            TASK_ID -> TasksContract.CONTENT_ITEM_TYPE

            DETAILS -> DetailsContract.CONTENT_TYPE

            DETAILS_ID -> DetailsContract.CONTENT_ITEM_TYPE

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert: starts with URI $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "insert: match is $match")

        val recordId: Long
        val returnUri: Uri

        when (match) {
            TASKS -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                recordId = db.insert(TasksContract.TABLE_NAME, null, values)
                if (recordId != -1L) {
                    returnUri = TasksContract.buildUriFromId(recordId)
                } else {
                    throw SQLException("Error inserting record")
                }
            }

            DETAILS -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                recordId = db.insert(DetailsContract.TABLE_NAME, null, values)
                if (recordId != -1L) {
                    returnUri = DetailsContract.buildUriFromId(recordId)
                } else {
                    throw SQLException("Error inserting record")
                }
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        Log.d(TAG, "insert: exiting and returning $recordId")
        return returnUri
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG, "query: starts with URI $uri")
        val match = uriMatcher.match(uri)

        val queryBuilder = SQLiteQueryBuilder()

        when (match) {
            TASKS -> queryBuilder.tables = TasksContract.TABLE_NAME

            TASK_ID -> {
                queryBuilder.tables = TasksContract.TABLE_NAME
                val taskId = TasksContract.getId(uri)
                queryBuilder.appendWhere("${TasksContract.Columns.TASK_ID} = ")
                queryBuilder.appendWhereEscapeString("$taskId")
            }

            DETAILS -> queryBuilder.tables = DetailsContract.TABLE_NAME

            DETAILS_ID -> {
                queryBuilder.tables = DetailsContract.TABLE_NAME
                val detailId = DetailsContract.getId(uri)
                queryBuilder.appendWhere("${DetailsContract.Columns.DETAIL_ID} = ")
                queryBuilder.appendWhereEscapeString("$detailId")
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDatabase.getInstance(context!!).readableDatabase
        val cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)

        Log.d(TAG, "query: rows in returned cursor are ${cursor.count}")  // TODO remove this later
        return cursor
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.d(TAG, "update: starts with URI $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "update: match is $match")

        val rowsAffected: Int
        var selectionCriteria: String

        when (match) {
            TASKS -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                rowsAffected = db.update(TasksContract.TABLE_NAME, values, selection, selectionArgs)
            }

            TASK_ID -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                val taskId = TasksContract.getId(uri)
                selectionCriteria = "${TasksContract.Columns.TASK_ID} = $taskId"
                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND $selection"
                }
                rowsAffected = db.update(TasksContract.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }

            DETAILS -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                rowsAffected = db.update(TasksContract.TABLE_NAME, values, selection, selectionArgs)
            }

            DETAILS_ID -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                val detailId = DetailsContract.getId(uri)
                selectionCriteria = "${DetailsContract.Columns.DETAIL_ID} = $detailId"
                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND $selection"
                }
                rowsAffected = db.update(DetailsContract.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        Log.d(TAG, "update: exiting, returning number of rows affected - $rowsAffected")
        return rowsAffected
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "delete: starts with URI $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "delete: match is $match")

        val rowsAffected: Int
        var selectionCriteria: String

        when (match) {
            TASKS -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                rowsAffected = db.delete(TasksContract.TABLE_NAME, selection, selectionArgs)
            }

            TASK_ID -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                val taskId = TasksContract.getId(uri)
                selectionCriteria = "${TasksContract.Columns.TASK_ID} = $taskId"
                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND $selection"
                }
                rowsAffected = db.delete(TasksContract.TABLE_NAME, selectionCriteria, selectionArgs)
            }

            DETAILS -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                rowsAffected = db.delete(DetailsContract.TABLE_NAME, selection, selectionArgs)
            }

            DETAILS_ID -> {
                val db = AppDatabase.getInstance(context!!).writableDatabase
                val detailId = DetailsContract.getId(uri)
                selectionCriteria = "${DetailsContract.Columns.DETAIL_ID} = $detailId"
                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND $selection"
                }
                rowsAffected = db.delete(DetailsContract.TABLE_NAME, selectionCriteria, selectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        Log.d(TAG, "delete: exiting, returning number of rows affected - $rowsAffected")
        return rowsAffected
    }
}