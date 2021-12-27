package com.aispeech.arch.view

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.aispeech.arch.databinding.ActivitySplashBinding
import com.aispeech.arch.repository.MSG_SPLASH_BANNER_DOWNLOAD_SUCCESS
import com.aispeech.arch.repository.MSG_SPLASH_BANNER_ERROR
import com.aispeech.arch.viewmode.SplashViewModel
import com.aispeech.arch.R
import com.aispeech.framework.fast.FastActivity
import com.bumptech.glide.Glide
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.stx.xhb.androidx.entity.BaseBannerInfo
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.arch.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : FastActivity<ActivitySplashBinding, SplashViewModel>(){
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadData()
    }
    //快捷生成vm
    private val vm: SplashViewModel by viewModels()

    override fun getViewModel(): SplashViewModel {
        mViewModel = vm
        return vm
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_splash
    }

    @OnMessage(key = MSG_SPLASH_BANNER_ERROR)
    fun onLoadBannerError(error: String) {
        ToastUtil.showShortToast(error)
    }

    @OnMessage(key = MSG_SPLASH_BANNER_DOWNLOAD_SUCCESS)
    fun onDownloadBannerSuccess(files: String) {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        val filesList = Gson().fromJson<List<String>>(files, listType)
        val banners = mutableListOf<BannerInfo>()
        filesList.forEach{
            banners.add(BannerInfo(it, ""))
        }
        mDataBinding.xBannerId.setBannerData(banners)
        mDataBinding.xBannerId.loadImage { _, model, view, _ ->
            Glide.with(this).load(((model as BannerInfo).xBannerUrl))
                .into(view as ImageView)
        }
    }

    class BannerInfo(private val url: String, private val title: String): BaseBannerInfo {
        override fun getXBannerUrl(): Any {
            return url
        }

        override fun getXBannerTitle(): String {
            return title
        }

    }
}