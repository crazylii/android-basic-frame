package com.aispeech.dds.observer

import android.util.Log
import com.aispeech.dui.dds.DDS
import com.aispeech.dui.dds.agent.MessageObserver
import com.aispeech.framework.extensions.toJson
import com.aispeech.idds.DUI_CHANNEL_COMMAND
import com.aispeech.idds.DUI_CHANNEL_MESSAGE
import com.aispeech.idds.commandList
import com.aispeech.idds.messageList
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.MessageManager
import com.yollpoll.arch.threadpool.ThreadPool
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by spq on 2021/12/16
 */
object DuiMessageObserver : MessageObserver {
    fun register() {
        DDS.getInstance().agent.subscribe(messageList, this)
    }

    fun unRegister() {
        DDS.getInstance().agent.unSubscribe(this)
    }

    override fun onMessage(message: String, data: String) {
        Log.d(TAG, "onMessage: $message data: $data")
        MessageManager.getInstance().sendMessage(message, data)
        MessageManager.getInstance()
            .sendMessage(DUI_CHANNEL_MESSAGE, DuiChannel(message, data).toJson())
    }
}