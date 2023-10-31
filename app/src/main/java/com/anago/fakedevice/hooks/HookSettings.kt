package com.anago.fakedevice.hooks

import android.content.ContentResolver
import android.provider.Settings.Secure
import com.anago.fakedevice.data.FakeDeviceData.devices
import com.anago.fakedevice.hooks.base.HookBase
import com.anago.fakedevice.utils.XposedUtils.invokeOriginalMethod
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
        // hooking String Secure#getString(ContentResolver resolver, String name)
        XposedHelpers.findAndHookMethod(
            clazzSecure,
            "getString",
            ContentResolver::class.java,
            String::class.java,
            fakeGetStringForUser()
        )
        // hooking String Secure#getStringForUser(ContentResolver resolver, String name, int userHandle)
        XposedHelpers.findAndHookMethod(
            clazzSecure,
            "getStringForUser",
            ContentResolver::class.java,
            String::class.java,
            Int::class.java,
            fakeGetStringForUser()
        )
        // hooking String System#getStringForUser(ContentResolver resolver, String name, int userHandle)
        XposedHelpers.findAndHookMethod(
            clazzSystem,
            "getStringForUser",
            ContentResolver::class.java,
            String::class.java,
            Int::class.java,
            fakeGetStringForUser()
        )
        // hooking String Global#getStringForUser(ContentResolver resolver, String name, int userHandle)
        XposedHelpers.findAndHookMethod(
            clazzGlobal,
            "getStringForUser",
            ContentResolver::class.java,
            String::class.java,
            Int::class.java,
            fakeGetStringForUser()
        )
        // hooking String NameValueCache#getStringForUser(ContentResolver cr, String name, final int userHandle)
        XposedHelpers.findAndHookMethod(
            clazzNameValueCache,
            "getStringForUser",
            ContentResolver::class.java,
            String::class.java,
            Int::class.java,
            fakeGetStringForUser()
        )
    }

    private fun fakeGetStringForUser(): XC_MethodReplacement {
        return object : XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                val name = param.args[1]
                if (name.equals(Secure.ANDROID_ID)) {
                    return devices["android_id"]
                }
                return param.invokeOriginalMethod()
            }
        }
    }
}