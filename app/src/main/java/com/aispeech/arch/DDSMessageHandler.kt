package com.aispeech.arch

import android.content.Context
import android.media.AudioManager
import com.aispeech.idds.*
import com.aispeech.idds.MEDIA_VOLUME_OPEN
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.arch.annotation.handler.MethodAnnotationHandler
import com.yollpoll.arch.log.LogUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.util.HashMap
import javax.inject.Inject

class DDSMessageHandler @Inject constructor(@ApplicationContext private val context: Context) {
    private val ddsMessageHandlerHelper : DDSMessageHandlerHelper by lazy { DDSMessageHandlerHelper(context) }
    fun init() {
        LogUtils.i("DDSMessageHandler 初始化")
        MethodAnnotationHandler().dispatch(this)
    }

    @OnMessage(key = DUI_CHANNEL_COMMAND)
    fun onCommandMessageHandle(data: String) {
        LogUtils.d("dds command message : $data")
        var jsonObject = JSONObject(data)
        val map: MutableMap<String, String> = HashMap()
        if (jsonObject.has("key")) {
            when (jsonObject.optString("key")) {
                MEDIA_VOLUME -> {
                    val json = JSONObject(jsonObject.optString("data"))
                    val currentVolume = ddsMessageHandlerHelper.setVolume(json.optString("volume"))
                    LogUtils.d( "ldc -> onEvent 音量调节 currentVolume:$currentVolume")
                    return
                }
                MEDIA_VOLUME_CLOSE -> {
                    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI)
                    LogUtils.d( "打开声音")
                    return
                }
                MEDIA_VOLUME_OPEN -> {
                    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI)
                    LogUtils.d( "静音")
                    return
                }
                MEDIA_PLAY, MEDIA_STOP, MEDIA_PAUSE, MEDIA_NEXT, MEDIA_PRE -> {
                    //暂时仅仅传递媒体控制事件
                    return
                }
            }
        }
        jsonObject = JSONObject(jsonObject.optString("data"))
        when {
            jsonObject.has("page") -> {
                if (jsonObject.has("topic")) {
                    //是否是主题（列表）页
                    map["topic"] = jsonObject.optString("topic")
                }
                val page = jsonObject.getString("page")
                map["page"] = page
            }
            jsonObject.has("audio_name") -> {
                val audioName = jsonObject.getString("audio_name")
                map["audio_name"] = audioName
            }
            jsonObject.has("seq") -> {
                val seq = jsonObject.getString("seq")
                map["seq"] = seq
            }
            jsonObject.has("speaker") -> {
                val speaker = jsonObject.getString("speaker")
                map["speaker"] = speaker
            }
            jsonObject.has("title") -> {
                map["title"] = jsonObject.optString("title")
            }
        }
        ddsMessageHandlerHelper.onIntent(params = map)
    }
}