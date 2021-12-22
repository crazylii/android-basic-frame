package com.aispeech.arch

import com.aispeech.dds.*
import com.aispeech.dui.dds.DDS
import com.aispeech.idds.*
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by spq on 2021/12/15
 * dds相关代理
 */
//class DDSProxy(dds: DdsImpl) : IDDS by dds
//class AsrProxy(asr: AsrImpl) : IAsr by asr
//class WakeupProxy(wakeup: WakeupImpl) : IWakeUp by wakeup
//class TtsProxy(tts: TtsImpl) : ITts by tts

@Module
@InstallIn(SingletonComponent::class)
abstract class DDSProxy {

    @Binds
    abstract fun bindDDS(ddsImpl: DdsImpl): IDDS


    @Binds
    abstract fun bindAsr(asrImpl: AsrImpl): IAsr


    @Binds
    abstract fun bindWakeup(wakeupImpl: WakeupImpl): IWakeUp


    @Binds
    abstract fun bindTts(ttsImpl: TtsImpl): ITts


}