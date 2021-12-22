package com.aispeech.dds.observer

import android.util.Log
import com.aispeech.dui.dds.DDS
import com.aispeech.dui.dsk.duiwidget.CommandObserver
import com.aispeech.framework.extensions.toJson
import com.aispeech.idds.DUI_CHANNEL_COMMAND
import com.aispeech.idds.SYS_ACTION_CALL
import com.aispeech.idds.commandList
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.MessageManager
import com.yollpoll.arch.message.liveeventbus.LiveEventBus
import com.yollpoll.arch.threadpool.ThreadPool
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by spq on 2021/12/16
 */


object DuiCommandObserver : CommandObserver {
    val messageFLow = MutableSharedFlow<String>()

    fun register() {
        DDS.getInstance().agent.subscribe(commandList, this)
    }

    fun unRegister() {
        DDS.getInstance().agent.unSubscribe(this)
    }

    override fun onCall(command: String, data: String) {
        Log.d(TAG, "onCommand: $command data: $data")
        //分发消息
        MessageManager.getInstance().sendMessage(command, data)
        MessageManager.getInstance()
            .sendMessage(DUI_CHANNEL_COMMAND, DuiChannel(command, data).toJson())
    }
}