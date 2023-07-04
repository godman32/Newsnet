package com.zym.zymresseler.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private var sp: SharedPreferences
    var spEditor: SharedPreferences.Editor

    fun prefClear() {
        spEditor.clear().commit()
    }

    fun setString(key: String?, value: String?) {
        spEditor.putString(key, value)
        spEditor.commit()
    }

    val sData: String
        get() = sp.getString(DATA, "").toString()


    companion object {
        const val PREF = "preference"
        const val DATA = "data"
    }

    init {
        sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        spEditor = sp.edit()
    }
}