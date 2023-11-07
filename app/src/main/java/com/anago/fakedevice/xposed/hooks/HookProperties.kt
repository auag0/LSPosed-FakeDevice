package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValue
import com.anago.fakedevice.xposed.hooks.base.HookBase
import com.anago.fakedevice.xposed.utils.XposedUtils.invokeOriginalMethod
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookProperties(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzProperties: Class<*> by lazy {
        XposedHelpers.findClass("java.util.Properties", classLoader)
    }

    override fun hook() {
        XposedHelpers.findAndHookMethod(clazzProperties, "getProperty", String::class.java, object: XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                val key = param.args[0] as String
                val result = param.invokeOriginalMethod()
                if(result != null && key == "http.agent") {
                    return getFakeDeviceValue(FakeDeviceDataType.HTTP_AGENT)
                }
                return result
            }
        })
    }
}