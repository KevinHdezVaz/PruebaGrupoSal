package com.hevaz.pruebagruposal.utils

import android.content.Context
import java.util.Locale

fun Context.saveAuthToken(token: String) {
    val sharedPreferences = getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("auth_token", token).apply()
}

fun Context.getAuthToken(): String? {
    val sharedPreferences = getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("auth_token", null)
}

fun Context.saveName(token: String) {
    val sharedPreferences = getSharedPreferences("NamePref", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("name_token", token).apply()
}

fun Context.getUserName(): String? {
    val sharedPreferences = this.getSharedPreferences("NamePref", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("name_token", null)
    return email?.substringBefore(".")?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}


fun Context.clearAuthToken() {
    val sharedPreferences = getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
    sharedPreferences.edit().remove("auth_token").apply()
}
fun Context.clearName() {
    val sharedPreferences = getSharedPreferences("NamePref", Context.MODE_PRIVATE)
    sharedPreferences.edit().remove("name_token").apply()
}
