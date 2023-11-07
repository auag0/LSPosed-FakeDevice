package com.anago.fakedevice.xposed

import com.anago.fakedevice.utils.Logger.logD
import com.anago.fakedevice.xposed.hooks.HookBluetoothAdapter
import com.anago.fakedevice.xposed.hooks.HookBluetoothDevice
import com.anago.fakedevice.xposed.hooks.HookBuild
import com.anago.fakedevice.xposed.hooks.HookDevicePolicyManager
import com.anago.fakedevice.xposed.hooks.HookProperties
import com.anago.fakedevice.xposed.hooks.HookSettings
import com.anago.fakedevice.xposed.hooks.HookSystemProperties
import com.anago.fakedevice.xposed.hooks.HookTelephonyManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage


class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        logD("loaded: ${lpparam.packageName}")
        val classLoader = lpparam.classLoader
        arrayOf(
            HookSystemProperties(classLoader),
            HookBuild(classLoader),
            HookSettings(classLoader),
            HookTelephonyManager(classLoader),
            HookBluetoothAdapter(classLoader),
            HookBluetoothDevice(classLoader),
            HookDevicePolicyManager(classLoader),
            HookProperties(classLoader)
        ).forEach {
            it.hook()
        }
    }
}