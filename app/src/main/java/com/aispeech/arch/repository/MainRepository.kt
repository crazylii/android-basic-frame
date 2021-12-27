package com.aispeech.arch.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.aispeech.arch.bean.PushBannerBean
import com.aispeech.arch.bean.TopicListBean
import com.aispeech.arch.net.HttpServiceFactory
import com.aispeech.arch.net.HttpService
import com.google.gson.Gson
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.message.MessageManager
import com.yollpoll.arch.threadpool.ThreadPool
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import java.util.function.Consumer
import javax.inject.Inject

/**
 * Created by spq on 2021/12/7
 */
class MainRepository @Inject constructor(){
    private val remoteRepository : HttpService = HttpServiceFactory.getInstance().createService(HttpService::class.java)

    private fun <T> enqueue(call: Call<T>, onResponse: Consumer<Response<T>>) {
        call.enqueue(object : Callback<T>{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<T>, response: Response<T>) {
                LogUtils.i("retrofit response:${response.body()?.toString()}")
                onResponse.accept(response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                call.cancel()
                LogUtils.e(t.toString())
            }

        })
    }

    private fun <T> enqueue(call: Call<T>, onResponse: Consumer<Response<T>>, onFailure: Consumer<Call<T>>) {
        call.enqueue(object : Callback<T>{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<T>, response: Response<T>) {
                LogUtils.i("retrofit response:${response.body()?.toString()}")
                onResponse.accept(response)
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onFailure(call: Call<T>, t: Throwable) {
                call.cancel()
                LogUtils.e(t.toString())
                onFailure.accept(call)
            }

        })
    }

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

    @RequiresApi(Build.VERSION_CODES.N)
    fun getSplashBanner(equipmentNo : String, path : File) {
        enqueue(remoteRepository.getSplashBanner(equipmentNo), (Consumer<Response<PushBannerBean>> { response ->
            LogUtils.i("轮播图url地址加载成功 value:${response.body()?.result.toString()}")
            if (response.isSuccessful) {
                if (response.body()?.success == true) {
                    ThreadPool.execute {
                        val files = mutableListOf<String>()
                        val latch = response.body()?.result?.size?.let { CountDownLatch(it) }
                        response.body()?.result?.forEach {
                            enqueue(remoteRepository.downloadBanner(it.shufflingImageUrl), (Consumer { downloadRes ->
                                if (downloadRes.isSuccessful) {
                                    ThreadPool.execute {
                                        try {
                                            downloadRes.body()?.byteStream().let { inputStream ->
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
                                } else {
                                    latch.also { la ->
                                        la?.countDown()
                                    }
                                    LogUtils.e("轮播图下载失败，错误error：${downloadRes.errorBody().toString()}")
                                }
                            }), (Consumer {
                                LogUtils.e("轮播图下载失败")
                                latch.also { la ->
                                    la?.countDown()
                                }
                            }))
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
        }))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTopicList(equipmentNo : String, topicName: String) {
        enqueue(remoteRepository.getTopicList(equipmentNo, topicName), (Consumer<Response<TopicListBean>> {
            LogUtils.i("主题列表数据加载成功")
            MessageManager.getInstance().sendMessage(MSG_TOPIC_LIST_SUCCESS, it.body())
        }))
    }
}