package com.aispeech.idds

import org.json.JSONObject

/**
 * Created by spq on 2021/12/15
 */
/**
 * dds相关方法
 */
interface IDDS {
    fun initDDS()
    fun startDialog()
    fun startDialog(jsonObject: JSONObject)
}

interface IAsr {
}

interface ITts {
    fun speak(text: String)
}

interface IWakeUp {
    fun enableWakeup(enable: Boolean)
}