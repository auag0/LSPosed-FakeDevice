package com.anago.fakedevice.utils

import android.util.Log

object Logger {
    private const val TAG = "Xposed-FakeDevice"
    fun logD(msg: Any?) {
        Log.d(TAG, msg.toString())
    }

    fun logE(msg: Any?) {
        Log.e(TAG, msg.toString())
    }
}