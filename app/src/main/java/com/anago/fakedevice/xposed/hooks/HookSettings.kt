package com.anago.fakedevice.xposed.hooks

import android.content.ContentResolver
import android.provider.Settings.Global
import android.provider.Settings.Secure
import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValue
import com.anago.fakedevice.utils.Logger
import com.anago.fakedevice.xposed.hooks.base.HookBase
import com.anago.fakedevice.xposed.utils.XposedUtils.invokeOriginalMethod
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookSettings(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzSecure: Class<*> by lazy {
        XposedHelpers.findClass("android.provider.Settings.Secure", classLoader)
    }
    private val clazzSystem: Class<*> by lazy {
        XposedHelpers.findClass("android.provider.Settings.System", classLoader)
    }
    private val clazzGlobal: Class<*> by lazy {
        XposedHelpers.findClass("android.provider.Settings.Global", classLoader)
    }
    private val clazzNameValueCache: Class<*> by lazy {
        XposedHelpers.findClass("android.provider.Settings.NameValueCache", classLoader)
    }

    override fun hook() {
        val settingsChildClass = arrayOf(clazzSecure, clazzSystem, clazzGlobal, clazzNameValueCache)
        settingsChildClass.forEach { clazz ->
            XposedHelpers.findAndHookMethod(
                clazz,
                "getStringForUser",
                ContentResolver::class.java,
                String::class.java,
                Int::class.java,
                fakeGetStringForUser()
            )
        }
    }

    private fun fakeGetStringForUser(): XC_MethodReplacement {
        return object : XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                val name = param.args[1]
                if (name == Secure.ANDROID_ID) {
                    Logger.logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.ANDROID_ID)
                }
                if (name == Global.DEVICE_NAME) {
                    Logger.logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.DEVICE)
                }
                if (name == "bluetooth_name") {
                    Logger.logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.BLUETOOTH_NAME)
                }
                if(name == Global.DEVELOPMENT_SETTINGS_ENABLED) {
                    Logger.logI("hooked ${param.method.name}")
                    return "0"
                }
                if(name == Global.ADB_ENABLED) {
                    Logger.logI("hooked ${param.method.name}")
                    return "0"
                }
                return param.invokeOriginalMethod()
            }
        }
    }
}