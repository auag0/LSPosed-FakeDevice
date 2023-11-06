package com.anago.fakedevice.xposed

import com.anago.fakedevice.BuildConfig
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage


class SelfHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (BuildConfig.APPLICATION_ID != lpparam.packageName) {
            return
        }
    }
}