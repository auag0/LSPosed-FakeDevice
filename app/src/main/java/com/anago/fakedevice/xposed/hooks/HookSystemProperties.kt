package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValueByPropKey
import com.anago.fakedevice.utils.Logger
import com.anago.fakedevice.xposed.hooks.base.HookBase
import com.anago.fakedevice.xposed.utils.XposedUtils.invokeOriginalMethod
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookSystemProperties(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzSystemProperties: Class<*> by lazy {
        XposedHelpers.findClass("android.os.SystemProperties", classLoader)
    }

    override fun hook() {
        // hooking SystemProperties#get(String key)
        XposedHelpers.findAndHookMethod(
            clazzSystemProperties,
            "get",
            String::class.java,
            fakeSystemPropertiesGet()
        )
        // hooking SystemProperties#get(String key, String def)
        XposedHelpers.findAndHookMethod(
            clazzSystemProperties,
            "get",
            String::class.java,
            String::class.java,
            fakeSystemPropertiesGet()
        )
    }

    private fun fakeSystemPropertiesGet(): XC_MethodReplacement {
        return object : XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                val key = param.args[0] as String
                val fakeDeviceValue = getFakeDeviceValueByPropKey(key)
                if (fakeDeviceValue != null) {
                    Logger.logI("hooked ${param.method.name}")
                    return fakeDeviceValue
                }
                return param.invokeOriginalMethod()
            }
        }
    }
}