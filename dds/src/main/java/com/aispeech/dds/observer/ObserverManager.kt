package com.aispeech.dds.observer

import com.squareup.moshi.JsonClass

/**
 * Created by spq on 2021/12/16
 */
internal const val TAG = "DuiObserver"

object ObserverManager {
    fun registerAll() {
        DuiCommandObserver.register()
        DuiMessageObserver.register()
        DuiNativeApiObserver.register()
        DuiUpdateObserver.register()
    }

    fun unRegisterAll() {
        DuiCommandObserver.unRegister()
        DuiMessageObserver.unRegister()
        DuiNativeApiObserver.unRegister()
        DuiUpdateObserver.unregister()
    }
}

@JsonClass(generateAdapter = true)
data class DuiChannel(val key: String, val data: String)