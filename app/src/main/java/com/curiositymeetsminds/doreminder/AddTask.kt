package com.curiositymeetsminds.doreminder

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*

private const val TAG = "AddTask"

class AddTask : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var spinnerText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)
        title = "Adding Task"
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ArrayAdapter.createFromResource(
            this, R.array.task_type,android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            taskTypeSpinner.adapter = it
        }
        taskTypeSpinner.onItemSelectedListener = this

        detailSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "switch turned on", Toast.LENGTH_SHORT).show()
                val callDialogBox = CallDialogBox()
                callDialogBox.show(supportFragmentManager, "CallDialogBox")
            } else {
                Toast.makeText(this, "switch turned off", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            val values = ContentValues().apply {
                put(TasksContract.Columns.TASK_NAME, addTaskName.text.toString())
                put(TasksContract.Columns.TASK_DESCRIPTION, addTaskDescription.text.toString())
                put(TasksContract.Columns.TASK_TYPE, taskType.text.toString())
            }
            val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(TAG, "onNothingSelected: called")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "onItemSelected: item at position $position selected")
        spinnerText = parent?.getItemAtPosition(position).toString()
        Log.d(TAG, "onItemSelected: spinner text is $spinnerText")
        taskType.text = spinnerText
    }

}
