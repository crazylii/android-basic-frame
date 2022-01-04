package com.aispeech.arch

import android.content.Context
import android.media.AudioManager
import com.aispeech.arch.view.SplashActivity
import com.aispeech.framework.ACTIVITY_TRANSFER_ROUTER
import com.google.gson.Gson
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.MessageManager

class DDSMessageHandlerHelper constructor(private val context: Context) {
    /**
     * 控制音量
     * @param action String? -/+
     */
    fun setVolume(action: String?): Int{
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        var currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        LogUtils.d("setVolume: max is $maxVolume current is $currentVolume")
        if (action == "-" && currentVolume > 0) {
            if (currentVolume < 10) {
                currentVolume = 0
            } else {
                currentVolume -= 10;
            }
        } else if (action == "+") {
            if (currentVolume > 90) {
                currentVolume = 100
            } else {
                currentVolume += 10

            }
        } else if (action?.endsWith("%") == true) {
            action.substring(0, action.indexOf("%")).apply {
                currentVolume = this.toInt()
            }
        } else {
            currentVolume = action?.toInt() ?: 0
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_SHOW_UI)
        return currentVolume
    }

    fun onIntent(params: MutableMap<String, String>?) {
            LogUtils.d("00000000000---->mCommandCallback-------${params?.get("page")}")
            val audioName = params?.contains("audio_name")//播放
            val seq = params?.contains("seq")//播放
            val speaker = params?.contains("speaker")//播放
            val title = params?.contains("title")//播放
            val json = Gson().toJson(params!!)
            if (audioName == true || seq == true || speaker == true || title == true) {
                LogUtils.i("onIntent: 资源播放")
                //
            } else {
                //根据page页面跳转
                when (params["page"]) {
                    "轮播" -> MessageManager.getInstance().sendMessage(ACTIVITY_TRANSFER_ROUTER, SplashActivity::class.java)
//                    else -> mChannel.invokeMethod(RoutPath.IMG_PAGE, json)
                }
            }
    }
}