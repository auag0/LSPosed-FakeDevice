package com.anago.fakedevice.hooks.base

open class HookBase(private val classLoader: ClassLoader) {
    open fun hook() {}
}