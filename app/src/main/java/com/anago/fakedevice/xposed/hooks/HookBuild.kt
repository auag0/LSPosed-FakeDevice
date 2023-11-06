package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValue
import com.anago.fakedevice.utils.Logger.logI
import com.anago.fakedevice.xposed.hooks.base.HookBase
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookBuild(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzBuild: Class<*> by lazy {
        XposedHelpers.findClass("android.os.Build", classLoader)
    }

    override fun hook() {
        // replace fields
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "ID",
            getFakeDeviceValue(FakeDeviceDataType.ID)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "DISPLAY",
            getFakeDeviceValue(FakeDeviceDataType.BUILD_ID)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "PRODUCT",
            getFakeDeviceValue(FakeDeviceDataType.NAME)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "DEVICE",
            getFakeDeviceValue(FakeDeviceDataType.DEVICE)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "BOARD",
            getFakeDeviceValue(FakeDeviceDataType.BOARD)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "MANUFACTURER",
            getFakeDeviceValue(FakeDeviceDataType.MANUFACTURER)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "BRAND",
            getFakeDeviceValue(FakeDeviceDataType.BRAND)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "MODEL",
            getFakeDeviceValue(FakeDeviceDataType.MODEL)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "HARDWARE",
            getFakeDeviceValue(FakeDeviceDataType.HARDWARE)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "BOOTLOADER",
            getFakeDeviceValue(FakeDeviceDataType.BOOTLOADER)
        )
        XposedHelpers.setStaticObjectField(
            clazzBuild,
            "FINGERPRINT",
            getFakeDeviceValue(FakeDeviceDataType.FINGERPRINT)
        )
        logI("replaced Build class")

        // hooking String deriveFingerprint()
        XposedHelpers.findAndHookMethod(
            clazzBuild,
            "deriveFingerprint",
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): Any {
                    logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.FINGERPRINT)
                }
            }
        )
    }
}