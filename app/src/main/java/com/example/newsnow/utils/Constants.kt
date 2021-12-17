package com.example.newsnow.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "04b7811d86a441118ca539537def9b62"
    const val DATABASE_NAME = "NEWS_ARTICLES"

    val KEY_CHIP_FILTER = stringPreferencesKey("selected_filter")
    const val PREF_NAME = "NEWS_PREFERENCE"

}