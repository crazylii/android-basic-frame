package com.aispeech.arch.net

import com.aispeech.arch.bean.PushBannerBean
import com.aispeech.arch.bean.TopicListBean
import com.example.nmbcompose.bean.Forum
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by spq on 2021/9/1
 */
interface HttpService {

    @GET(FORUM_LIST)
    suspend fun getForum(): List<Forum>

    @GET(SPLASH_BANNER_LIST)
    fun getSplashBanner(@Query("equipmentNo") equipmentNo : String) : Call<PushBannerBean>

    @Streaming
    @GET
    fun downloadBanner(@Url url : String) : Call<ResponseBody>

    @GET(TOPIC_LIST_GET)
    fun getTopicList(@Query("equipmentNo") equipmentNo : String, @Query("topicName") topicName: String): Call<TopicListBean>
}