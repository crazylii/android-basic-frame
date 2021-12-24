package com.aispeech.arch.repository

import com.aispeech.arch.bean.PushBannerBean
import com.aispeech.arch.net.HttpServiceFactory
import com.aispeech.arch.net.HttpService
import com.google.gson.Gson
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.MessageManager
import com.yollpoll.arch.threadpool.ThreadPool
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.Runnable
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

/**
 * Created by spq on 2021/12/7
 */
class MainRepository @Inject constructor(){
    private val remoteRepository : HttpService = HttpServiceFactory.getInstance().createService(HttpService::class.java)

    //模拟数据请求
    //可以替换成数据库或者网络请求
    companion object {
        //    @JvmStatic
        suspend fun getData(pageNo: Int, pageSize: Int): List<String> {
            delay(2000)
            val res = arrayListOf<String>()
            for (i in 0..pageSize) {
                res.add("itemNo is $i / pageNo is $pageNo / page size is $pageSize")
            }
            return res
        }
    }

    fun getSplashBanner(equipmentNo : String, path : File) {
        remoteRepository.getSplashBanner(equipmentNo).enqueue(object : Callback<PushBannerBean> {
            override fun onResponse(
                call: Call<PushBannerBean>,
                response: Response<PushBannerBean>
            ) {
                LogUtils.i("轮播图url地址加载成功 value:${response.body()?.result.toString()}")
                if (response.isSuccessful) {
                    if (response.body()?.success == true) {
                        val files = mutableListOf<String>()
                        val latch = response.body()?.result?.size?.let { CountDownLatch(it) }
                        ThreadPool.execute {
                            response.body()?.result?.forEach {
                                remoteRepository.downloadBanner(it.shufflingImageUrl).let { cl ->
                                    cl.enqueue(object : Callback<ResponseBody> {
                                        override fun onResponse(
                                            call: Call<ResponseBody>,
                                            response: Response<ResponseBody>
                                        ) {
                                            if (response.isSuccessful) {
                                                ThreadPool.execute{
                                                    try {
                                                        response.body()?.byteStream().let { inputStream ->
                                                            val b = ByteArray(8 * 1024)
                                                            var count = 0
                                                            val fileName = it.shufflingImageUrl.substring(0, it.shufflingImageUrl.indexOf("?x-oss-process")).let {str ->  File(str).name }
                                                            val file = File(path, fileName)
                                                            val out = FileOutputStream(file)
                                                            while (inputStream?.read(b).also { c ->
                                                                    if (c != null) {
                                                                        count = c
                                                                    }
                                                                } != -1) {
                                                                out.write(b, 0, count)
                                                            }
                                                            inputStream?.close()
                                                            out.close()
                                                            files.add(file.path)
                                                        }
                                                    } catch (e: Exception) {
                                                        LogUtils.e(e.toString())
                                                    } finally {
                                                        latch.also { la ->
                                                            la?.countDown()
                                                        }
                                                    }
                                                }
                                            } else{
                                                latch.also { la ->
                                                    la?.countDown()
                                                }
                                                LogUtils.e("轮播图下载失败，错误error：${response.errorBody().toString()}")
                                            }
                                        }

                                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                            LogUtils.e("轮播图下载失败，error：${t}")
                                            latch.also { la ->
                                                la?.countDown()
                                            }
                                        }
                                    })
                                }
                            }
                            latch.also { it?.await() }
                            response.body()?.result?.size?.let {
                                if (files.size == it) {
                                    Gson().toJson(files).let { json ->
                                        LogUtils.d("轮播图下载成功：${json}")
                                        MessageManager.getInstance().sendMessage(MSG_SPLASH_BANNER_DOWNLOAD_SUCCESS, json)
                                    }
                                } else {
                                    LogUtils.e("轮播图下载不完全，作失败处理")
                                }
                            }
                        }
                    } else {
                        LogUtils.e(response.body()?.message)
                        MessageManager.getInstance().sendMessage(MSG_SPLASH_BANNER_ERROR, response.body()?.message)
                    }
                } else {
                    LogUtils.e(response.toString())
                }
            }

            override fun onFailure(call: Call<PushBannerBean>, t: Throwable) {
                LogUtils.e(t.toString())
            }

        })
    }
}