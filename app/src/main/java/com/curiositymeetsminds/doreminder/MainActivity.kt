package com.curiositymeetsminds.doreminder

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.EventLogTags
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import javax.net.ssl.HttpsURLConnection

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: starts")
        val db = AppDatabase.getInstance(this).readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${TasksContract.TABLE_NAME}", null)

        Log.d(TAG, "*****************************************************************************")
        cursor.use {
            while (it.moveToNext()) {
                with (it) {
                    val id = getLong(0)
                    val name = getString(1)
                    val description = getString(2)
                    val taskType = getString(3)
                    val result = "ID: $id, Name: $name, Description: $description, Task Type: $taskType"
                    Log.d(TAG, result)
                }
            }
        }
        Log.d(TAG, "*****************************************************************************")

//        var number: String = "1111111111"
//        var message: String = "How are you?"
//
//        val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=who is Ritesh"))
//        button.setOnClickListener (View.OnClickListener {
//            Log.d(TAG, "Button CLicked")
//            if (sendIntent.resolveActivity(packageManager) != null) {
//                startActivity(sendIntent)
//            }
//        })
    }
}
