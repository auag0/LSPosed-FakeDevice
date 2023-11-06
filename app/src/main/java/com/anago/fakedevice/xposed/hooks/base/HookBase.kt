package com.anago.fakedevice.xposed.hooks.base

open class HookBase(private val classLoader: ClassLoader) {
    open fun hook() {}
}