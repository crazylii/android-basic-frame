package com.aispeech.framework.extensions

import com.aispeech.framework.fast.FastViewModel
import com.yollpoll.arch.base.BaseApplication


/**
 * Created by spq on 2021/4/27
 */
/////////////////////////////////////DataStore 对vm的拓展/////////////////////////////////////

suspend fun FastViewModel.saveIntToDataStore(key: String, value: Int) {
    context.putInt(key, value)
}

suspend fun FastViewModel.getIntByDataStore(key: String, default: Int): Int {
    return context.getInt(key, default)
}

suspend fun FastViewModel.saveStringToDataStore(key: String, value: String) {
    context.putString(key, value)
}

suspend fun FastViewModel.getStringByDataStore(key: String, default: String): String {
    return context.getString(key, default)
}

suspend fun FastViewModel.saveFloatToDataStore(key: String, default: Float) {
    context.putFloat(key, default)
}

suspend fun FastViewModel.getFloatByDataStore(key: String, default: Float): Float {
    return context.getFloat(key, default)
}

suspend fun FastViewModel.saveLongToDataStore(key: String, value: Long) {
    context.putLong(key, value)
}

suspend fun FastViewModel.getLongByDataStore(key: String, default: Long): Long {
    return context.getLong(key, default)
}

suspend fun FastViewModel.saveDoubleToDataStore(key: String, value: Double) {
    context.putDouble(key, value)
}

suspend fun FastViewModel.getDoubleByDataStore(key: String, default: Double): Double {
    return context.getDouble(key, default)
}

suspend fun FastViewModel.saveBean(key: String, value: Any) {
    context.saveBean(key, value)
}

suspend inline fun <reified T> FastViewModel.getBean(key: String): T? {
    return getStringByDataStore(key, "").toJsonBean()
}

suspend fun FastViewModel.saveList(key: String, value: List<*>) {
    context.saveList(key, value)
}

suspend inline fun <reified T> FastViewModel.getList(key: String): List<T>? {
    return getStringByDataStore(key, "").toListBean()
}

suspend inline fun <reified T, reified K> FastViewModel.saveMap(key: String, value: Map<T, K>) {
    context.saveMap(key, value)
}

suspend inline fun <reified T, reified K> FastViewModel.getMap(key: String): Map<T, K>? {
    return getStringByDataStore(key, "").toMapBean()
}