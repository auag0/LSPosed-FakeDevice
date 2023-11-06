package com.anago.fakedevice.xposed.utils

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import java.lang.reflect.Member

object XposedUtils {
    fun XC_MethodHook.MethodHookParam.invokeOriginalMethod(
        method: Member = this.method,
        thisObject: Any? = this.thisObject,
        args: Array<Any?> = this.args,
    ): Any? {
        return XposedBridge.invokeOriginalMethod(method, thisObject, args)
    }
}