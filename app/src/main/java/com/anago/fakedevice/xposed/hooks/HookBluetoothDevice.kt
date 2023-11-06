package com.anago.fakedevice.xposed.hooks

import com.anago.fakedevice.data.FakeDeviceData.FakeDeviceDataType
import com.anago.fakedevice.data.FakeDeviceData.getFakeDeviceValue
import com.anago.fakedevice.utils.Logger
import com.anago.fakedevice.xposed.hooks.base.HookBase
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class HookBluetoothDevice(classLoader: ClassLoader) : HookBase(classLoader) {
    private val clazzBluetoothDevice: Class<*> by lazy {
        XposedHelpers.findClass("android.bluetooth.BluetoothDevice", classLoader)
    }

    override fun hook() {
        XposedHelpers.findAndHookMethod(
            clazzBluetoothDevice,
            "getName",
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): String {
                    Logger.logI("hooked ${param.method.name}")
                    return getFakeDeviceValue(FakeDeviceDataType.BLUETOOTH_NAME)
                }
            })
    }
}