package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.pref

import android.content.SharedPreferences
import android.util.Log
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserPreferenceImpl(private val pref: SharedPreferences) : UserPreference {
    // private val preference = SharedPreferenceUtils.createPreference(context, PREF_NANE)

    override fun isUsingGridMode(): Boolean {
        return pref.getBoolean(KEY_IS_USING_GRID_MODE, false)
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        pref[KEY_IS_USING_GRID_MODE] = isUsingGridMode
        Log.e(TAG, "Saving value user pref $isUsingGridMode")
    }

    companion object {
        const val TAG = "User Preferences"
        const val PREF_NANE = "foodapp-pref"
        const val KEY_IS_USING_GRID_MODE = "KEY_IS_USING_GRID_MODE"
    }
}
