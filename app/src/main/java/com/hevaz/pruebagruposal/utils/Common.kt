package com.hevaz.pruebagruposal.utils

import android.content.Context

fun Context.saveAuthToken(token: String) {
    val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("auth_token", token).apply()
}

fun Context.getAuthToken(): String? {
    val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getString("auth_token", null)
}
