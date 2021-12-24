package com.aispeech.arch.net

import com.aispeech.framework.net.http.RetrofitFactory
import com.aispeech.framework.net.http.RetrofitIntercept
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.Serializable

/**
 * Created by spq on 2021/4/20
 */
var okHttpClient: OkHttpClient? = null

class HttpServiceFactory private constructor() : Serializable {
    private val retrofitFactory: RetrofitFactory = RetrofitFactory(object : RetrofitIntercept {
        override fun baseUrl(): String {
            return BASE_URL
        }

        override fun okHttpClientBuilder(builder: OkHttpClient.Builder) {

        }

        override fun retrofit(retrofit: Retrofit) {
        }

        override fun okHttpClient(client: OkHttpClient) {
            okHttpClient = client
        }

        override fun retrofitBuilder(builder: Retrofit.Builder) {
        }

    })

    companion object {
        fun getInstance() = SingletonHolder.mInstance
    }

    fun <T> createService(clazz: Class<T>) = retrofitFactory.createService(clazz)


    private object SingletonHolder {
        //内部类
        val mInstance = HttpServiceFactory()
    }

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }
}