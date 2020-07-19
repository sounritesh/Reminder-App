package com.curiositymeetsminds.doreminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        title = "Your Tasks"

        var taskList = ArrayList<TaskData>()
//        val values = ContentValues().apply {
//            put(TasksContract.Columns.TASK_NAME, "Email RSR")
//            put(TasksContract.Columns.TASK_TYPE, TaskType.EMAIL)
//        }
//
//        val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)
//        Log.d(TAG, "testInsert: $uri")
        Log.d(TAG, "onCreate: starts")
        val cursor = contentResolver.query(TasksContract.CONTENT_URI, null, null, null, null)

        cursor.use {
            while (it?.moveToNext()!!) {
                with (it) {
                    val id = getLong(0)
                    val name = getString(1)
                    val desc = getString(2)
                    val type = getString(3)
                    taskList.add(TaskData(id, name, desc, type))
                }
            }
        }

        recyclerView.adapter = TaskAdapter(taskList)
        recyclerView.layoutManager = LinearLayoutManager(this)

//      Delete functionality
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback (0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                taskList.removeAt(pos)
                contentResolver.delete(TasksContract.buildUriFromId(viewHolder.itemView.tag.toString().toLong()), null, null)
                val adapter = recyclerView.adapter
                adapter?.notifyItemRemoved(pos)
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recyclerView)


//        Log.d(TAG, "*****************************************************************************")
//        cursor.use {
//            while (it?.moveToNext()!!) {
//                with (it) {
//                    val id = getLong(0)
//                    val name = getString(1)
//                    val description = getString(2)
//                    val taskType = getString(3)
//                    val result = "ID: $id, Name: $name, Description: $description, Task Type: $taskType"
//                    Log.d(TAG, result)
//                }
//            }
//        }
//        Log.d(TAG, "*****************************************************************************")

        fab.setOnClickListener {
            val intent =  Intent(this, AddTask::class.java)
            startActivity(intent)
        }

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

//    private fun testInsert() {
//
//    }

}
