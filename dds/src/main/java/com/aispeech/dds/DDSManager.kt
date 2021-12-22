package com.aispeech.dds

import android.content.Context
import com.aispeech.dds.observer.DuiCommandObserver
import com.aispeech.dds.observer.DuiMessageObserver
import com.aispeech.dui.dds.DDS
import com.aispeech.dui.dds.agent.ASREngine
import com.aispeech.dui.dsk.duiwidget.DuiWidget
import com.aispeech.idds.*
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.util.ToastUtil
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by spq on 2021/12/15
 */
class DdsImpl @Inject constructor(
    @ApplicationContext val context: Context
) : IDDS {
    override fun initDDS() {
//        ToastUtil.showShortToast("start init")
        DDSService.start(context)
    }

    override fun startDialog() {
        DDS.getInstance().agent.startDialog()
    }

    override fun startDialog(jsonObject: JSONObject) {
        DDS.getInstance().agent.startDialog(jsonObject)
    }
}

class AsrImpl @Inject constructor(
    @ApplicationContext val context: Context
) : IAsr {
    fun startListen(
        onBegin: (() -> Unit)?,
        onEnd: (() -> Unit)? = null,
        onBufferReceived: ((ByteArray?) -> Unit)? = null,
        onPartialResults: ((String?) -> Unit)? = null,
        onFinalResults: ((String?) -> Unit)? = null,
        onError: ((String?) -> Unit)? = null,
        onRmsChanged: ((Float?) -> Unit)? = null,
    ) {
        DDS.getInstance().agent.asrEngine.startListening(object : ASREngine.Callback {
            override fun beginningOfSpeech() {
                onBegin?.invoke()
            }

            override fun endOfSpeech() {
                onEnd?.invoke()
            }

            override fun bufferReceived(p0: ByteArray?) {
                onBufferReceived?.invoke(p0)
            }

            override fun partialResults(p0: String?) {
                onPartialResults?.invoke(p0)
            }

            override fun finalResults(p0: String?) {
                onFinalResults?.invoke(p0)
            }

            override fun error(p0: String?) {
                onError?.invoke(p0)
            }

            override fun rmsChanged(p0: Float) {
                onRmsChanged?.invoke(p0)
            }

        })
    }
}

class WakeupImpl @Inject constructor(
    @ApplicationContext val context: Context
) : IWakeUp {
    override fun enableWakeup(enable: Boolean) {
        LogUtils.d("dds state : %s", DDS.getInstance().initStatus)
        if (enable) {
            DDS.getInstance().agent.wakeupEngine.enableWakeup()
        } else {
            DDS.getInstance().agent.wakeupEngine.disableWakeup()
        }
    }

}

class TtsImpl @Inject constructor(
    @ApplicationContext val context: Context
) : ITts {
    override fun speak(text: String) {
        DDS.getInstance().agent.ttsEngine.speak(text, 1)
    }

}

//class NativeApiImpl @Inject constructor() {
//    fun feedBack(api: String) {
//    }
//}