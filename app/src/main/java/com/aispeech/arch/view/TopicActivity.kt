package com.aispeech.arch.view

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.aispeech.arch.R
import com.aispeech.arch.bean.TopicListBean
import com.aispeech.arch.databinding.ActivityTopicBinding
import com.aispeech.arch.repository.MSG_TOPIC_LIST_SUCCESS
import com.aispeech.arch.viewmode.TopicViewMode
import com.aispeech.framework.fast.FastActivity
import com.google.gson.Gson
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.arch.log.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopicActivity : FastActivity<ActivityTopicBinding, TopicViewMode>() {
    //快捷生成vm
    private val vm: TopicViewMode by viewModels()

    @Inject
    lateinit var adapter: CusAdapter
    override fun getViewModel(): TopicViewMode {
        mViewModel = vm
        return vm
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_topic
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        lifecycleScope.launch {
            vm.loadData("老兵口述史")
        }
    }

    @OnMessage(key = MSG_TOPIC_LIST_SUCCESS)
    fun onTopicData(topicList: TopicListBean) {
        LogUtils.d(Gson().toJson(topicList))
    }

    class CusAdapter @Inject constructor(): BaseAdapter() {

        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            TODO("Not yet implemented")
        }

    }
}