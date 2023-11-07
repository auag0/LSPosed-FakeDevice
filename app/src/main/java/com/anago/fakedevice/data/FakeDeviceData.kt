package com.anago.fakedevice.data

object FakeDeviceData {
    fun getFakeDeviceValue(type: FakeDeviceDataType): String {
        return type.name
    }

    fun getFakeDeviceValueByPropKey(propKey: String): String? {
        val type = when (propKey) {
            "ro.build.id" -> FakeDeviceDataType.ID
            "ro.build.display.id" -> FakeDeviceDataType.BUILD_ID
            "ro.product.name" -> FakeDeviceDataType.NAME
            "ro.product.device" -> FakeDeviceDataType.DEVICE
            "ro.product.board" -> FakeDeviceDataType.BOARD
            "ro.product.manufacturer" -> FakeDeviceDataType.MANUFACTURER
            "ro.product.brand" -> FakeDeviceDataType.BRAND
            "ro.product.model" -> FakeDeviceDataType.MODEL
            "ro.hardware" -> FakeDeviceDataType.HARDWARE
            "ro.bootloader" -> FakeDeviceDataType.BOOTLOADER
            "ro.build.fingerprint" -> FakeDeviceDataType.FINGERPRINT
            else -> return null
        }
        return getFakeDeviceValue(type)
    }

    fun getFakeDeviceTypeAndBuildFields(): Map<String, FakeDeviceDataType> {
        return mapOf(
            "ID" to FakeDeviceDataType.ID,
            "DISPLAY" to FakeDeviceDataType.BUILD_ID,
            "PRODUCT" to FakeDeviceDataType.NAME,
            "DEVICE" to FakeDeviceDataType.DEVICE,
            "BOARD" to FakeDeviceDataType.BOARD,
            "MANUFACTURER" to FakeDeviceDataType.MANUFACTURER,
            "BRAND" to FakeDeviceDataType.BRAND,
            "MODEL" to FakeDeviceDataType.MODEL,
            "HARDWARE" to FakeDeviceDataType.HARDWARE,
            "BOOTLOADER" to FakeDeviceDataType.BOOTLOADER,
            "FINGERPRINT" to FakeDeviceDataType.FINGERPRINT
        )
    }

    enum class FakeDeviceDataType {
        ID,
        BUILD_ID,
        NAME,
        DEVICE,
        BOARD,
        MANUFACTURER,
        BRAND,
        MODEL,
        HARDWARE,
        BOOTLOADER,
        FINGERPRINT,
        ANDROID_ID,
        IMEI,
        IMSI,
        BLUETOOTH_NAME
    }
}