package com.aispeech.arch

import com.aispeech.dds.DDSRouterManager
import com.aispeech.demo.TestModuleManager
import com.yollpoll.arch.base.BaseApplication
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.router.Dispatcher
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by spq on 2021/11/30
 */
@HiltAndroidApp
class MyApplication : BaseApplication() {
    @Inject
    lateinit var ddsMessageHandler : DDSMessageHandler
    override fun onCreate() {
        super.onCreate()
        LogUtils.init(this)
        registerRouter()
        ddsMessageHandler.init()
    }

    /**
     * 注册路由
     */
    private fun registerRouter() {
        val map = hashMapOf(
            Pair("dds", DDSRouterManager),
            Pair("demo", TestModuleManager)
        )
        Dispatcher.registerDispatch(map)
    }


}