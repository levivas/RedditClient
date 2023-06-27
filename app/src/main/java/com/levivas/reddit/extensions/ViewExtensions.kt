package com.levivas.reddit.extensions

import android.view.View

fun View.setOnSafeClickListener(listenerBlock: (View) -> Unit) =
    setOnClickListener(OnSafeClickListener(listenerBlock))
