package com.aispeech.dds.view

import android.app.Application
import android.view.View
import com.aispeech.dds.DDSService
import com.aispeech.dds.R
import com.aispeech.dds.WakeupImpl
import com.aispeech.dds.databinding.ActivityDdsBinding
import com.aispeech.framework.fast.FastActivity
import com.aispeech.framework.fast.FastViewModel
import com.aispeech.idds.DDS_MSG_INIT_FINISH
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.arch.annotation.ViewModel

@ViewModel(DDSVM::class)
class DDsActivity : FastActivity<ActivityDdsBinding, DDSVM>() {
    override fun getContentViewId(): Int {
        return R.layout.activity_dds
    }

    public fun initDDS(view: View) {
        DDSService.start(context = this)
    }

    @OnMessage(key = DDS_MSG_INIT_FINISH)
    fun onInitDDS() {
        WakeupImpl(this).enableWakeup(true)
    }
}

class DDSVM(application: Application) : FastViewModel(application) {

}