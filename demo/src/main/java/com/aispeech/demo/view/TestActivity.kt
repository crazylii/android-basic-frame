package com.aispeech.demo.view

import android.util.Log
import androidx.activity.viewModels
import com.aispeech.demo.R
import com.aispeech.demo.databinding.ActivityTestBinding
import com.aispeech.demo.viewmodel.TestViewModel
import com.aispeech.framework.fast.FastActivity
import com.aispeech.idds.*
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.arch.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by spq on 2021/12/15
 */
private const val TAG = "TestActivity"

@AndroidEntryPoint
class TestActivity : FastActivity<ActivityTestBinding, TestViewModel>() {
    //快捷生成vm
    private val vm: TestViewModel by viewModels()

    @Inject
    lateinit var wakeupImpl: IWakeUp

    //替换掉框架里的vm
    override fun getViewModel(): TestViewModel {
        mViewModel = vm
        return vm
    }

    //绑定layoutId
    override fun getContentViewId(): Int {
        return R.layout.activity_test
    }

    @OnMessage(key = com.aispeech.idds.DDS_MSG_INIT_FINISH)
    fun onDDSInitFinish() {
        ToastUtil.showShortToast("DDS初始化成功~")
        wakeupImpl.enableWakeup(true)
    }

    //收到duiMessage的消息
    //key是注册的message action
    @OnMessage(key = com.aispeech.idds.CONTEXT_INPUT_TEXT)
    fun onDuiMessage(data: String) {
        var cur = mDataBinding.tvMessage.text.toString()
        cur += data + "\n"
        mDataBinding.tvMessage.text = data
    }

    @OnMessage(key = com.aispeech.idds.DUI_CHANNEL_MESSAGE)
    fun onDuiAllMessage(json: String) {
        Log.d(TAG, "onDuiAllMessage: $json")
    }

}