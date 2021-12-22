package com.aispeech.arch

import com.aispeech.dds.DDSRouterManager
import com.aispeech.dds.observer.DuiCommandObserver
import com.aispeech.dds.observer.DuiMessageObserver
import com.aispeech.dds.observer.DuiNativeApiObserver
import com.aispeech.demo.TestModuleManager
import com.yollpoll.arch.base.BaseApplication
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.router.Dispatcher
import com.yollpoll.arch.router.OnDispatchListener
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by spq on 2021/11/30
 */
@HiltAndroidApp
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        LogUtils.init(this, "demo", true)
        registerRouter()
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