package com.curiositymeetsminds.doreminder

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "AppDatabase"

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "DoReminder.db"

internal class AppDatabase private constructor(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate: initialising database")
        val createTableTasks = """CREATE TABLE ${TasksContract.TABLE_NAME} 
            (${TasksContract.Columns.TASK_ID} INTEGER PRIMARY KEY NOT NULL,
            ${TasksContract.Columns.TASK_NAME} TEXT NOT NULL,
            ${TasksContract.Columns.TASK_DESCRIPTION} TEXT,
            ${TasksContract.Columns.TASK_TYPE});
        """.replaceIndent(" ")
        Log.d(TAG, createTableTasks)
        db.execSQL(createTableTasks)

        val createTableDetails = """CREATE TABLE ${DetailsContract.TABLE_NAME}
            (${DetailsContract.Columns.DETAIL_ID} INTEGER PRIMARY KEY NOT NULL,
            ${DetailsContract.Columns.DETAIL_MOBILE_NUMBER} TEXT,
            ${DetailsContract.Columns.DETAIL_EMAIL_ID} TEXT,
            ${DetailsContract.Columns.DETAIL_SUBJECT} TEXT,
            ${DetailsContract.Columns.DETAIL_BODY} TEXT,
            ${DetailsContract.Columns.DETAIL_SEARCH_QUERY} TEXT);
        """.replaceIndent(" ")
        Log.d(TAG, createTableDetails)
        db.execSQL(createTableDetails)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade: starts")
        when (oldVersion) {
            1 -> {
//                upgrade logic from version 1
            } else -> throw IllegalAccessException("onUpgrade: called with unknown new version $newVersion")
        }
    }

    companion object: SingletonHolder<AppDatabase, Context>(::AppDatabase)
}