package com.flepper.therapeutic.data.apppreference

import com.flepper.therapeutic.di.KMMContext

actual object KMMPreference {
    actual fun KMMContext.putInt(key: String, value: Int) {
    }

    actual fun KMMContext.getInt(
        key: String,
        default: Int
    ): Int {
       return 0
    }

    actual fun KMMContext.putString(
        key: String,
        value: String
    ) {
    }

    actual fun KMMContext.getString(key: String): String? {
       return null
    }

    actual fun KMMContext.putBool(
        key: String,
        value: Boolean
    ) {
    }

    actual fun KMMContext.getBool(
        key: String,
        default: Boolean
    ): Boolean {
        return false
    }

}