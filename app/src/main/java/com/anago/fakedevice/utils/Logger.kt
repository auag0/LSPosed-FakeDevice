package com.anago.fakedevice.utils

import android.util.Log

object Logger {
    private const val TAG = "LSPosed-FakeDevice"
    fun logD(msg: Any?) {
        Log.d(TAG, msg.toString())
    }

    fun logI(msg: Any?) {
        Log.i(TAG, msg.toString())
    }

    fun logE(msg: Any?) {
        Log.e(TAG, msg.toString())
    }
}