package com.aispeech.arch.net

/**
 * Created by spq on 2021/6/30
 */
const val IS_DEV = false
private const val master_domain = "http://121.196.11.197:8991"
private const val prod_domain = "http://116.62.224.170:8991"
val BASE_URL = if (IS_DEV) master_domain else prod_domain

const val FORUM_LIST = "Api/getForumList"

const val SPLASH_BANNER_LIST = "/v1/app/rotation/list"