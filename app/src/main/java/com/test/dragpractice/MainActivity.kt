package com.test.dragpractice

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private var img: ImageView? = null
    private var rootLayout: ViewGroup? = null
    private var xPoint = 0
    private var yPoint = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootLayout = findViewById<ConstraintLayout>(R.id.cst_root) as ViewGroup
        img = rootLayout!!.findViewById<ImageView>(R.id.img_item) as ImageView
        val layoutParams = ConstraintLayout.LayoutParams(150, 150)
        img!!.layoutParams = layoutParams
        img!!.setOnTouchListener(ChoiceTouchListener())
    }

    private inner class ChoiceTouchListener : OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = view.layoutParams as ConstraintLayout.LayoutParams
                    xPoint = x - lParams.leftMargin
                    yPoint = y - lParams.topMargin
                }
                MotionEvent.ACTION_UP -> {
                    val itemParams = view.layoutParams as ConstraintLayout.LayoutParams
                    Log.d(
                        "UP Point || X",
                        itemParams.leftMargin.toString() + "" + ", Y" + itemParams.topMargin
                    )
                }
                MotionEvent.ACTION_POINTER_DOWN -> {}
                MotionEvent.ACTION_POINTER_UP -> {}
                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.leftMargin = x - xPoint
                    layoutParams.topMargin = y - yPoint
                    view.layoutParams = layoutParams
                }
            }
            rootLayout!!.invalidate()
            return true
        }
    }
}
