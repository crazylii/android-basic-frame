package com.aispeech.demo

import android.content.Context
import com.aispeech.demo.view.TestActivity
import com.aispeech.framework.extensions.startActivity
import com.yollpoll.arch.router.OnBackListener
import com.yollpoll.arch.router.OnDispatchListener
import java.util.HashMap

/**
 * Created by spq on 2021/12/15
 */
object TestModuleManager : OnDispatchListener {
    override fun onDispatch(
        params: HashMap<String, String>?,
        context: Context,
        onBackListener: OnBackListener?
    ) {
        val module = params?.get("module")
        //module参数
        when (module) {
            "test" -> {
                context.startActivity<TestActivity>()
            }
        }
    }
}