package com.aispeech.arch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.aispeech.arch.databinding.ActivityMainBinding
import com.aispeech.arch.databinding.ItemPagingTestBinding
import com.aispeech.arch.view.SplashActivity
import com.aispeech.dds.observer.ObserverManager
import com.aispeech.framework.extensions.shortToast
import com.aispeech.framework.fast.FastActivity
import com.aispeech.framework.paging.BasePagingDataAdapter
import com.aispeech.idds.DDS_MSG_INIT_FINISH
import com.yollpoll.annotation.annotation.MethodReference
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.arch.annotation.PermissionAuto
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.arch.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

@AndroidEntryPoint
@PermissionAuto
//@ViewModel(MainViewModel::class)
class MainActivity : FastActivity<ActivityMainBinding, MainViewModel>() {
    //快捷生成vm
    private val vm: MainViewModel by viewModels()

    override fun getViewModel(): MainViewModel {
        mViewModel = vm
        return vm
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFlowData()
        ToastUtil.showShortToast("DDS初始化")

        vm.idds.initDDS()
    }

    /////////////////mvvm//////////////////////
    @OnMessage
    fun onTimesChange(times: Int) {
        Log.d(TAG, "onTimesChange: $times")
        mDataBinding.tvTitle.text = "View中收到的Time:$times"
    }

    /////////////////paging//////////////////////
    val adapter =
        BasePagingDataAdapter<String>(
            R.layout.item_paging_test, BR.content,
            onBindViewHolder = { position, holder ->
                (holder.binding as ItemPagingTestBinding).tvItem.setOnClickListener {
                    ToastUtil.showLongToast("OnClick $position")
                }
            },
            onBindPlaceHolder = { pos, binding ->
                Log.d(TAG, "onBindPlaceHolder $pos")
                (binding as ItemPagingTestBinding).tvItem.setBackgroundColor(resources.getColor(R.color.black))
            }
        )
    val manager = LinearLayoutManager(context)

    //加载flow中的数据
    private fun loadFlowData() {
        adapter.addLoadStateListener { loadStates ->
            //loadSates加载状态，notLoading,loading,error.表示三种加载状态
            Log.e(TAG, "loadFlowData: ${loadStates.toString()}")

            when (loadStates.refresh) {
                is LoadState.Loading ->
                    mDataBinding.refreshProgress.visibility = View.VISIBLE
                else -> mDataBinding.refreshProgress.visibility = View.GONE
            }

            when (loadStates.append) {
                is LoadState.Loading ->
                    mDataBinding.loadProgress.visibility = View.VISIBLE
                else -> mDataBinding.loadProgress.visibility = View.GONE
            }
        }
        lifecycleScope.launch {
            viewModel.pagingFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /////////////////@PreExecute//////////////////////
    @MethodReference
    fun checkData(): Boolean {
        if (mDataBinding.edtInput2.text.isEmpty()) {
            "请输入内容".shortToast()
            return false
        }
        return true
    }

    /////////////////DDS//////////////////////
    fun initDDS(view: View) {
        val params = hashMapOf(
            Pair("key", "value"),
            Pair("key2", "value2")
        )
//        val url = SchemeBuilder()
//            .scheme(RouterScheme.NATIVE)
//            .host("demo")
//            .module("test")
//            .params(params)
//            .build()
//        val url = SchemeBuilder()
//            .scheme(RouterScheme.NATIVE)
//            .host("dds")
//            .module("initDDS")
//            .params(params)
//            .build()
//        url.shortToast()
//        Dispatcher.dispatch(url, context)

//        ToastUtil.showShortToast("DDS初始化")
//        DDSService.start(context = this)
//        Dispatcher.dispatch("native://dds?module=initDDS", context)

        startActivity(Intent(this, SplashActivity::class.java))
    }

    @OnMessage(key = DDS_MSG_INIT_FINISH)
    fun onDDSInitFinish() {
        LogUtils.d("DDS初始化成功，开始调用唤醒， 跳转轮播")
        ObserverManager.registerAll()
        ToastUtil.showShortToast("DDS初始化成功")
        vm.iWakeUp.enableWakeup(true)
        startActivity(Intent(this, SplashActivity::class.java))
    }

}