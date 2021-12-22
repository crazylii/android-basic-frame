package com.aispeech.arch

import kotlinx.coroutines.*

/**
 * Created by spq on 2021/12/7
 */
object MainRepository {
    //模拟数据请求
    //可以替换成数据库或者网络请求
    @JvmStatic
    suspend fun getData(pageNo: Int, pageSize: Int): List<String> {
        delay(2000)
        val res = arrayListOf<String>()
        for (i in 0..pageSize) {
            res.add("itemNo is $i / pageNo is $pageNo / page size is $pageSize")
        }
        return res
    }

}