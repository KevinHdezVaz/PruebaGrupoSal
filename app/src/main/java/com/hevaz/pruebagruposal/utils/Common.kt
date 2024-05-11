package com.hevaz.pruebagruposal.utils

import android.content.Context
import com.hevaz.pruebagruposal.data.local.User
import java.util.Locale

fun Context.saveAuthToken(token: String) {
    val sharedPreferences = getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("auth_token", token).apply()
}

fun Context.getAuthToken(): String? {
    val sharedPreferences = getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("auth_token", null)
}

fun Context.saveUserData( user: User) {
    val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("UserFirstName", user.first_name)
        putString("UserLastName", user.last_name)
        putString("UserEmail", user.email)
        putString("UserAvatar", user.avatar)
        apply()
    }
}
fun clearUserData(context: Context) {
    val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
    sharedPreferences.edit().clear().apply()
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
