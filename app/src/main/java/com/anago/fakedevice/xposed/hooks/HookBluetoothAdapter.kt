package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValue
import com.anago.fakedevice.utils.Logger.logI
import com.anago.fakedevice.xposed.hooks.base.HookBase
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookBluetoothAdapter(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzBluetoothAdapter: Class<*> by lazy {
        XposedHelpers.findClass("android.bluetooth.BluetoothAdapter", classLoader)
    }

    override fun hook() {
        XposedHelpers.findAndHookMethod(
            clazzBluetoothAdapter,
            "getName",
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): String? {
                    logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.BLUETOOTH_NAME)
                }
            })
    }
}