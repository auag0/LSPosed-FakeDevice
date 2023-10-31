package com.anago.fakedevice.data

import android.os.Build

object FakeDeviceData {
    var devices: HashMap<String, String> = hashMapOf()

    var buildFieldNameAndFakeValue: Map<String, String?> = emptyMap()

    init {
        devices["ro.build.id"] = "id"
        devices["ro.product.name"] = "name"
        devices["ro.product.device"] = "device"
        devices["ro.product.board"] = "board"
        devices["ro.product.manufacturer"] = "manufacturer"
        devices["ro.product.brand"] = "brand"
        devices["ro.product.model"] = "model"
        devices["ro.hardware"] = "hardware"
        devices["ro.bootloader"] = "bootloader"
        devices["ro.build.fingerprint"] = generateFakeFingerprint()// last
        devices["android_id"] = "android id"
        devices["imei"] = "imei"
        devices["imsi"] = "imsi"

        buildFieldNameAndFakeValue = mapOf(
            "ID" to devices["ro.build.id"],
            "PRODUCT" to devices["ro.product.name"],
            "DEVICE" to devices["ro.product.device"],
            "BOARD" to devices["ro.product.board"],
            "MANUFACTURER" to devices["ro.product.manufacturer"],
            "BRAND" to devices["ro.product.brand"],
            "MODEL" to devices["ro.product.model"],
            "HARDWARE" to devices["ro.hardware"],
            "BOOTLOADER" to devices["ro.bootloader"],
            "FINGERPRINT" to devices["ro.build.fingerprint"]
        )
    }

    private fun generateFakeFingerprint(): String {
        val brand = devices["ro.product.board"]
        val product = devices["ro.product.name"]
        val device = devices["ro.product.device"]
        val release = Build.VERSION.RELEASE
        val id = devices["ro.build.id"]
        val incremental = Build.VERSION.INCREMENTAL
        val type = Build.TYPE
        val tags = Build.TAGS
        return "$brand/$product/$device:$release/$id/$incremental:$type/$tags"
    }
}