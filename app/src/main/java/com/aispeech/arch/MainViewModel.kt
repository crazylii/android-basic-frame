package com.aispeech.arch

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aispeech.framework.extensions.*
import com.aispeech.framework.fast.FastViewModel
import com.aispeech.framework.paging.BasePagingSource
import com.aispeech.framework.paging.getCommonPager
import com.aispeech.arch.net.HttpServiceFactory
import com.aispeech.arch.repository.MainRepository
import com.squareup.moshi.JsonClass
import com.yollpoll.arch.annotation.PreExecute
import com.yollpoll.arch.binding.bindingwrapper.command.BindingCommand
import com.yollpoll.arch.message.MessageManager
import com.aispeech.arch.net.HttpService
import kotlinx.coroutines.launch

/**
 * Created by spq on 2021/11/30
 */
class MainViewModel(application: Application) : FastViewModel(application) {
    ////////////////////////////////mvvm框架////////////////////////////////
    @Bindable
    var times = 0

    @Bindable
    var text = ""
        get() = "hello word: $times"

    fun addition() {
        times++
        notifyChange()
        //发送消息给activity
        MessageManager.getInstance().sendMessage<Int>(MR.MainActivity_onTimesChange, times)
    }

    ////////////////////////////paging库加载数据////////////////////////////
    val pagingFlow = getCommonPager {
        return@getCommonPager object : BasePagingSource<String>() {
            override suspend fun load(pos: Int): List<String> {
                return MainRepository.getData(pos, 20)
            }
        }
    }.flow.cachedIn(viewModelScope)

    ////////////////////////////dataStore////////////////////////////
    val saveKey = "text"

    @Bindable
    var saveContent = ""
    fun saveText(text: String) {
        viewModelScope.launch {
            saveStringToDataStore(saveKey, text)
            "保存成功".shortToast()
            saveContent = ""
            notifyPropertyChanged(BR.saveContent)
        }
    }

    fun showSave() {
        viewModelScope.launch {
            getStringByDataStore(saveKey, "null").shortToast()
        }
    }

    ////////////////////////////bindingCommand////////////////////////////
    @PreExecute("MainActivity&&checkData")
    val command = BindingCommand.build {
        "测试成功".shortToast()
    }

    ////////////////////////////moshiTest////////////////////////////
    @JsonClass(generateAdapter = true)
    data class TestJsonBean(val name: String = "name", val no: Int = 1)

    val map = hashMapOf<String, String>(
        Pair("first", "first"),
        Pair("second", "second"),
        Pair("third", "third"),
        Pair("forth", "forth"),
    )
    val list = arrayListOf<TestJsonBean>(
        TestJsonBean(),
        TestJsonBean(),
        TestJsonBean(),
        TestJsonBean(),
    )

    fun testMoshi() {
        val json1 = TestJsonBean().toJson()
        val json2 = map.toMapJson()
        val json3 = list.toListJson()
        "$json1 ____ $json2 _____ $json3".shortToast()
        Log.d("MainViewModel", "testMoshi: $json1 ____ $json2 _____ $json3")

    }

    ////////////////////////////retrofit////////////////////////////
    fun loadNetData() {
        viewModelScope.launch {
            val forumList =
                HttpServiceFactory.getInstance().createService(HttpService::class.java).getForum()

            "加载结果: 长度_${forumList.size}".shortToast()
            Log.d("MainViewModel", "netData>>>>>${forumList.toListJson()}")
        }
    }

    ///////////////////////////hilt///////////////////////////////

}