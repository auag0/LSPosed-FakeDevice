package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceTypeAndBuildFields
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
        getFakeDeviceTypeAndBuildFields().forEach { (fieldName, type) ->
            run {
                XposedHelpers.setStaticObjectField(
                    clazzBuild,
                    fieldName,
                    getFakeDeviceValue(type)
                )
                logI("replaced Build field: $fieldName")
            }
        }

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