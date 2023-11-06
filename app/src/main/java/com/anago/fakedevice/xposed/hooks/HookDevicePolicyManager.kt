package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValue
import com.anago.fakedevice.utils.Logger
import com.anago.fakedevice.xposed.hooks.base.HookBase
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookDevicePolicyManager(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzDevicePolicyManager: Class<*> by lazy {
        XposedHelpers.findClass("android.app.admin.DevicePolicyManager", classLoader)
    }

    override fun hook() {
        XposedHelpers.findAndHookMethod(
            clazzDevicePolicyManager,
            "getEnrollmentSpecificId",
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): Any {
                    Logger.logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.ANDROID_ID)
                }
            })
    }
}