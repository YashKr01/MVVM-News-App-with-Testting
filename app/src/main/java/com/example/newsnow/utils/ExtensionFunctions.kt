package com.example.newsnow.utils

import android.view.View
import android.widget.Button

object ExtensionFunctions {

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.INVISIBLE
    }

}