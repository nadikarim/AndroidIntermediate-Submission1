package com.nadikarim.submision1.utils

import android.content.Context
import com.nadikarim.submision1.data.model.UserSession

class LoginPreference (context: Context) {

    val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(user: UserSession) {
        val editor = preferences.edit()
        editor.putString(NAME, user.name)
        editor.putString(TOKEN, user.token)
        editor.putString(USER_ID, user.userId)
        editor.putBoolean(LOGIN_STATE, user.isLogin)
        editor.apply()
    }

    fun logout() {
        val editor = preferences.edit()
        editor.remove(NAME)
        editor.remove(TOKEN)
        editor.putBoolean(LOGIN_STATE, false)
        editor.apply()
    }

    fun getUser(): UserSession {
        return UserSession(
            preferences.getString(NAME, "") ?: "",
            preferences.getString(TOKEN, "")?: "",
            preferences.getString(USER_ID, "")?: "",
            preferences.getBoolean(LOGIN_STATE, false)
        )
    }
}