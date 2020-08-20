package com.curiositymeetsminds.doreminder

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException
import java.text.FieldPosition

private const val TAG = "ListClickListener"

class ListClickListener (context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener) : RecyclerView.SimpleOnItemTouchListener() {

    interface OnRecyclerClickListener {
        fun onItemClick (view: View, position: Int)
    }

    private val gestureDetector = GestureDetectorCompat (context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapUp: starts")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onSingleTapUp: calling listener.onItemClick()")
            if (childView != null) {
                listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return true
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val result = gestureDetector.onTouchEvent(e)
        return result
    }
}