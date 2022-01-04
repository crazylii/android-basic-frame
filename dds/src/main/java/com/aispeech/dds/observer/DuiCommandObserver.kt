package com.aispeech.dds.observer

import com.aispeech.dui.dds.DDS
import com.aispeech.dui.dsk.duiwidget.CommandObserver
import com.aispeech.framework.extensions.toJson
import com.aispeech.idds.DUI_CHANNEL_COMMAND
import com.aispeech.idds.commandList
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.MessageManager
/**
 * Created by spq on 2021/12/16
 */


object DuiCommandObserver : CommandObserver {

    fun register() {
        DDS.getInstance().agent.subscribe(commandList, this)
    }

    fun unRegister() {
        DDS.getInstance().agent.unSubscribe(this)
    }

    override fun onCall(command: String, data: String) {
        LogUtils.d("onCommand: $command data: $data")
        //分发消息
        MessageManager.getInstance().sendMessage(command, data)
        MessageManager.getInstance()
            .sendMessage(DUI_CHANNEL_COMMAND, DuiChannel(command, data).toJson())
    }
}