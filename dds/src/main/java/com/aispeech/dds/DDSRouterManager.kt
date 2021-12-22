package com.aispeech.dds

import android.content.Context
import android.util.Log
import com.aispeech.framework.extensions.startActivity
import com.aispeech.framework.extensions.toJson
import com.aispeech.framework.extensions.toMapJson
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.liveeventbus.LiveEventBus
import com.yollpoll.arch.router.OnBackListener
import com.yollpoll.arch.router.OnDispatchListener
import java.util.HashMap

/**
 * Created by spq on 2021/12/14
 * 路由管理，跳转页面用
 */
object DDSRouterManager : OnDispatchListener {
    override fun onDispatch(
        params: HashMap<String, String>?,
        context: Context,
        onBackListener: OnBackListener?
    ) {
        when (params?.get("module")) {
            "initDDS" -> {
                context.startActivity<DDsActivity>()
            }
        }
    }

}