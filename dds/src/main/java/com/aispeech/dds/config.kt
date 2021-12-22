package com.aispeech.dds

import android.provider.Settings
import com.aispeech.dui.dds.DDSConfig
import java.util.*


/**
 * Created by spq on 2021/12/14
 * dds config
 */
//var androidId: String = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
const val productId = "279601547"
const val productKey = "b09011f35fcda7c71dbbdf70c9145bec"
const val productSecret = "4c3a19e8a54440a753bd193740f299d9"
const val apiKey = "b6abc538f1dbb6abc538f1db61b847d0"
const val aliasKey = "test"
const val deviceId = "deviceId"

var config = DDSConfig().addConfig(DDSConfig.K_DEVICE_ID, deviceId)
    .addConfig(DDSConfig.K_PRODUCT_ID, productId)
    .addConfig(DDSConfig.K_USER_ID, "userId")
    .addConfig(DDSConfig.K_ALIAS_KEY, aliasKey)
    .addConfig(DDSConfig.K_API_KEY, apiKey)
    .addConfig(DDSConfig.K_PRODUCT_KEY, productKey)
    .addConfig(DDSConfig.K_PRODUCT_SECRET, productSecret)
    .addConfig(DDSConfig.K_CUSTOM_ZIP, "product.zip")
    .addConfig(DDSConfig.K_DUICORE_ZIP, "duicore.zip")//该配置的用法详见下方"注意"
    .addConfig(DDSConfig.K_USE_UPDATE_DUICORE, "false")
// 资源更新配置项
// config.addConfig(DDSConfig.K_DUICORE_ZIP, "duicore.zip"); // 预置在指定目录下的DUI内核资源包名, 避免在线下载内核消耗流量, 推荐使用
// config.addConfig(DDSConfig.K_CUSTOM_ZIP, "product.zip"); // 预置在指定目录下的DUI产品配置资源包名, 避免在线下载产品配置消耗流量, 推荐使用
// config.addConfig(DDSConfig.K_USE_UPDATE_DUICORE, "false"); //设置为false可以关闭dui内核的热更新功能，可以配合内置dui内核资源使用
// config.addConfig(DDSConfig.K_USE_UPDATE_NOTIFICATION, "false"); // 是否使用内置的资源更新通知栏

// 录音配置项
// config.addConfig(DDSConfig.K_RECORDER_MODE, "internal"); //录音机模式：external（使用外置录音机，需主动调用拾音接口）、internal（使用内置录音机，DDS自动录音）
// config.addConfig(DDSConfig.K_IS_REVERSE_AUDIO_CHANNEL, "false"); // 录音机通道是否反转，默认不反转
// config.addConfig(DDSConfig.K_AUDIO_SOURCE, AudioSource.DEFAULT); // 内置录音机数据源类型
// config.addConfig(DDSConfig.K_AUDIO_BUFFER_SIZE, (16000 * 1 * 16 * 100 / 1000)); // 内置录音机读buffer的大小

// TTS配置项
// config.addConfig(DDSConfig.K_STREAM_TYPE, AudioManager.STREAM_MUSIC); // 内置播放器的STREAM类型
// config.addConfig(DDSConfig.K_TTS_MODE, "internal"); // TTS模式：external（使用外置TTS引擎，需主动注册TTS请求监听器）、internal（使用内置DUI TTS引擎）
// config.addConfig(DDSConfig.K_CUSTOM_TIPS, "{\"71304\":\"请讲话\",\"71305\":\"不知道你在说什么\",\"71308\":\"咱俩还是聊聊天吧\"}"); // 指定对话错误码的TTS播报。若未指定，则使用产品配置。

//唤醒配置项
// config.addConfig(DDSConfig.K_WAKEUP_ROUTER, "dialog"); //唤醒路由：partner（将唤醒结果传递给partner，不会主动进入对话）、dialog（将唤醒结果传递给dui，会主动进入对话）
// config.addConfig(DDSConfig.K_WAKEUP_BIN, "/sdcard/wakeup.bin"); //商务定制版唤醒资源的路径。如果开发者对唤醒率有更高的要求，请联系商务申请定制唤醒资源。
// config.addConfig(DDSConfig.K_ONESHOT_MIDTIME, "500");// OneShot配置：
// config.addConfig(DDSConfig.K_ONESHOT_ENDTIME, "2000");// OneShot配置：

//识别配置项
// config.addConfig(DDSConfig.K_ASR_ENABLE_PUNCTUATION, "false"); //识别是否开启标点
// config.addConfig(DDSConfig.K_ASR_ROUTER, "dialog"); //识别路由：partner（将识别结果传递给partner，不会主动进入语义）、dialog（将识别结果传递给dui，会主动进入语义）
// config.addConfig(DDSConfig.K_VAD_TIMEOUT, 5000); // VAD静音检测超时时间，默认8000毫秒
// config.addConfig(DDSConfig.K_ASR_ENABLE_TONE, "true"); // 识别结果的拼音是否带音调
// config.addConfig(DDSConfig.K_ASR_TIPS, "true"); // 识别完成是否播报提示音
// config.addConfig(DDSConfig.K_VAD_BIN, "/sdcard/vad.bin"); // 商务定制版VAD资源的路径。如果开发者对VAD有更高的要求，请联系商务申请定制VAD资源。

// 调试配置项
// config.addConfig(DDSConfig.K_CACHE_PATH, "/sdcard/cache"); // 调试信息保存路径,如果不设置则保存在默认路径"/sdcard/Android/data/包名/cache"
// config.addConfig(DDSConfig.K_WAKEUP_DEBUG, "true"); // 用于唤醒音频调试, 开启后在 "/sdcard/Android/data/包名/cache" 目录下会生成唤醒音频
// config.addConfig(DDSConfig.K_VAD_DEBUG, "true"); // 用于过vad的音频调试, 开启后在 "/sdcard/Android/data/包名/cache" 目录下会生成过vad的音频
// config.addConfig(DDSConfig.K_ASR_DEBUG, "true"); // 用于识别音频调试, 开启后在 "/sdcard/Android/data/包名/cache" 目录下会生成识别音频
// config.addConfig(DDSConfig.K_TTS_DEBUG, "true");  // 用于tts音频调试, 开启后在 "/sdcard/Android/data/包名/cache/tts/" 目录下会自动生成tts音频

// 麦克风阵列配置项
// config.addConfig(DDSConfig.K_MIC_TYPE, "1"); // 设置硬件采集模组的类型 0：无。默认值。 1：单麦回消 2：线性四麦 3：环形六麦 4：车载双麦 5：家具双麦 6: 环形四麦  7: 新车载双麦
// config.addConfig(DDSConfig.K_MIC_ARRAY_AEC_CFG, "/data/aec.bin"); // 麦克风阵列aec资源的磁盘绝对路径,需要开发者确保在这个路径下这个资源存在
// config.addConfig(DDSConfig.K_MIC_ARRAY_BEAMFORMING_CFG, "/data/beamforming.bin"); // 麦克风阵列beamforming资源的磁盘绝对路径，需要开发者确保在这个路径下这个资源存在

// 全双工/半双工配置项
// config.addConfig(DDSConfig.K_DUPLEX_MODE, "HALF_DUPLEX");// 半双工模式
// config.addConfig(DDSConfig.K_DUPLEX_MODE, "FULL_DUPLEX");// 全双工模式

// 声纹配置项
// config.addConfig(DDSConfig.K_VPRINT_ENABLE, "true");// 是否使用声纹
// config.addConfig(DDSConfig.K_USE_VPRINT_IN_WAKEUP, "true");// 是否与唤醒结合使用声纹
// config.addConfig(DDSConfig.K_VPRINT_BIN, "/sdcard/vprint.bin");// 声纹资源的绝对路径

// asrpp配置荐
// config.addConfig(DDSConfig.K_USE_GENDER, "true");// 使用性别识别
// config.addConfig(DDSConfig.K_USE_AGE, "true");// 使用年龄识别

