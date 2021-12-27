package com.aispeech.demo

import android.content.Context
import com.aispeech.idds.IAsr
import com.aispeech.idds.IDDS
import com.aispeech.idds.ITts
import com.aispeech.idds.IWakeUp
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * Created by spq on 2021/12/16
 * 依赖注入中全局DDS对象
 */
object AppCenter {
    //非android模块使用hilt
    fun getDDSEntryPoint(context: Context) =
        EntryPointAccessors.fromApplication(context, DDSEntryPoint::class.java)

    //非android模块使用hilt
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface DDSEntryPoint {
        fun getDDS(): IDDS
        fun getWakeUp(): IWakeUp
        fun getAsr(): IAsr
        fun getTts(): ITts
    }

}