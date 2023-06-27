package com.levivas.reddit.extensions

import android.view.View

private const val CLICK_INTERVAL = 1000L

class OnSafeClickListener(private val onSafeClick: (View) -> Unit) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime >= CLICK_INTERVAL) {
            lastClickTime = currentClickTime
            onSafeClick(v)
        }
    }
}
