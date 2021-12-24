package com.aispeech.dds.observer

import com.aispeech.dui.dds.DDS
import com.aispeech.dui.dds.agent.MessageObserver
import com.aispeech.dui.dds.update.DDSUpdateListener
import com.aispeech.idds.SYS_RESOURCE_UPDATED
import com.yollpoll.arch.log.LogUtils

/**
 * Created by spq on 2021/12/16
 */
object DuiUpdateObserver : MessageObserver{
    override fun onMessage(p0: String?, p1: String?) {
        LogUtils.i("dds 更新信息：p0:$p0\n p1:$p1")
        initUpdate()
    }

    fun register() {
        LogUtils.i("注册dds资源更新")
        DDS.getInstance().agent.subscribe(SYS_RESOURCE_UPDATED, this)
        initUpdate()
    }

    fun unregister() {
        LogUtils.i("注销dds资源更新")
        DDS.getInstance().agent?.unSubscribe(this)
    }

    private fun initUpdate() {
        DDS.getInstance().updater.update(object : DDSUpdateListener{
            override fun onUpdateFound(p0: String?) {
                LogUtils.i("onUpdateFound dds 资源: $p0")
                DDS.getInstance().agent.ttsEngine.speak("发现新版本,正在为您更新", 1)
            }

            override fun onUpdateFinish() {
                LogUtils.i("onUpdateFinish dds 资源")
                // 更新成功后不要立即调用speak提示用户更新成功, 这个时间DDS正在初始化
                DDS.getInstance().agent.ttsEngine.speak("更新完成", 1)
            }

            override fun onDownloadProgress(p0: Float) {
//                LogUtils.i("onDownloadProgress dds 资源 progress:$p0")
            }

            override fun onError(p0: Int, p1: String?) {
                LogUtils.d("dds更新失败， error：$p1")
            }

            override fun onUpgrade(p0: String?) {
                LogUtils.d("更新失败, version:$p0 -> 当前sdk版本过低，和dui平台上的dui内核不匹配，请更新sdk")
            }

        })
    }
}