package com.levivas.reddit.extensions

import android.util.Log

inline fun <T> tryOrNull(f: () -> T) =
    try {
        f()
    } catch (e: Exception) {
        Log.d("Debug warning issue", "exception: ${e.message}")
        null
    }