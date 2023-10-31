package com.anago.fakedevice

import com.anago.fakedevice.hooks.HookBuild
import com.anago.fakedevice.hooks.HookSettings
import com.anago.fakedevice.hooks.HookSystemProperties
import com.anago.fakedevice.hooks.HookTelephonyManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val classLoader = lpparam.classLoader
        arrayOf(
            HookSystemProperties(classLoader),
            HookBuild(classLoader),
            HookSettings(classLoader),
            HookTelephonyManager(classLoader)
        ).forEach {
            it.hook()
        }
    }
}