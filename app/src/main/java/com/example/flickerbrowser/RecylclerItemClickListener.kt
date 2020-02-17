package com.example.flickerbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecylclerItemClickListener(ctx : Context, recyclerView :RecyclerView, val listener : OnRecyclerClickListener) : RecyclerView.SimpleOnItemTouchListener() {
    val Tag = "R"
    interface  OnRecyclerClickListener{
        fun onItemClick(v : View, pos:Int)
        fun onItemLongClick(v: View, pos:Int)
    }

    val gestureDetector = GestureDetectorCompat(ctx, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val curView = recyclerView.findChildViewUnder(e.x,e.y) ?: return false

            listener.onItemClick(curView, recyclerView.getChildAdapterPosition(curView)  )
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val curView = recyclerView.findChildViewUnder(e.x,e.y)

            if(curView!=null)
                listener.onItemLongClick(curView, recyclerView.getChildAdapterPosition(curView)  )
        }
    } )

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(e)
    }
}