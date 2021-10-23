package com.example.newsnow.utils

import android.view.View

object ExtensionFunctions {

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.INVISIBLE
    }

    fun String.trimDate(): String = this.substring(0, 10)

}