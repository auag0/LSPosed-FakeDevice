package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValue
import com.anago.fakedevice.utils.Logger.logI
import com.anago.fakedevice.xposed.hooks.base.HookBase
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
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): Any {
                    logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.ANDROID_ID)
                }
            }
        )
        // hooking String getDeviceId(int slotIndex)
        XposedHelpers.findAndHookMethod(
            clazzTelephonyManager,
            "getDeviceId",
            Int::class.java,
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): Any {
                    logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.ANDROID_ID)
                }
            }
        )
        // hooking String getImei(int slotIndex)
        XposedHelpers.findAndHookMethod(
            clazzTelephonyManager,
            "getImei",
            Int::class.java,
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): Any {
                    logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.IMEI)
                }
            }
        )
        // hooking String getSubscriberId(int subId)
        XposedHelpers.findAndHookMethod(
            clazzTelephonyManager,
            "getSubscriberId",
            Int::class.java,
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): Any {
                    logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.IMSI)
                }
            }
        )
    }
}