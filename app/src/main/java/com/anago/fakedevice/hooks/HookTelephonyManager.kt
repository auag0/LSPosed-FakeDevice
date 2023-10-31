package com.anago.fakedevice.hooks

import com.anago.fakedevice.data.FakeDeviceData.devices
import com.anago.fakedevice.hooks.base.HookBase
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookTelephonyManager(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzTelephonyManager: Class<*> by lazy {
        XposedHelpers.findClass("android.telephony.TelephonyManager", classLoader)
    }

    override fun hook() {
        // hooking String getDeviceId()
        XposedHelpers.findAndHookMethod(
            clazzTelephonyManager,
            "getDeviceId",
            XC_MethodReplacement.returnConstant(devices["android_id"])
        )
        // hooking String getDeviceId(int slotIndex)
        XposedHelpers.findAndHookMethod(
            clazzTelephonyManager,
            "getDeviceId",
            Int::class.java,
            XC_MethodReplacement.returnConstant(devices["android_id"])
        )
        // hooking String getImei(int slotIndex)
        XposedHelpers.findAndHookMethod(
            clazzTelephonyManager,
            "getImei",
            Int::class.java,
            XC_MethodReplacement.returnConstant(devices["imei"])
        )
        // hooking String getSubscriberId(int subId)
        XposedHelpers.findAndHookMethod(
            clazzTelephonyManager,
            "getSubscriberId",
            Int::class.java,
            XC_MethodReplacement.returnConstant(devices["imsi"])
        )
    }
}