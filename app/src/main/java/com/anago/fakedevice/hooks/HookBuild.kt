package com.anago.fakedevice.hooks

import com.anago.fakedevice.data.FakeDeviceData.buildFieldNameAndFakeValue
import com.anago.fakedevice.data.FakeDeviceData.devices
import com.anago.fakedevice.hooks.base.HookBase
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookBuild(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzBuild: Class<*> by lazy {
        XposedHelpers.findClass("android.os.Build", classLoader)
    }

    override fun hook() {
        // replace fields
        buildFieldNameAndFakeValue.forEach { (fieldName: String, fakeValue: String?) ->
            if (fakeValue != null) {
                XposedHelpers.setStaticObjectField(clazzBuild, fieldName, fakeValue)
            }
        }

        // hooking String deriveFingerprint()
        XposedHelpers.findAndHookMethod(
            clazzBuild,
            "deriveFingerprint",
            XC_MethodReplacement.returnConstant(devices["ro.build.fingerprint"])
        )
    }
}