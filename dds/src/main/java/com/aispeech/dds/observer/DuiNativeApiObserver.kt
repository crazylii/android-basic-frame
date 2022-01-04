package com.aispeech.dds.observer

import com.aispeech.dui.dds.DDS
import com.aispeech.dui.dsk.duiwidget.NativeApiObserver
import com.aispeech.framework.extensions.toJson
import com.aispeech.idds.DUI_CHANNEL_NATIVE_API
import com.aispeech.idds.messageList
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.MessageManager

/**
 * Created by spq on 2021/12/16
 */
object DuiNativeApiObserver : NativeApiObserver {
    fun register() {
        DDS.getInstance().agent.subscribe(messageList, this)
    }

    fun unRegister() {
        DDS.getInstance().agent.unSubscribe(this)
    }

    override fun onQuery(api: String, data: String) {
        LogUtils.d("onNative: $api data:$data")
        MessageManager.getInstance().sendMessage(api, data)
        MessageManager.getInstance()
            .sendMessage(DUI_CHANNEL_NATIVE_API, DuiChannel(api, data).toJson())
    }
}