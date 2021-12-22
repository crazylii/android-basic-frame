package com.aispeech.demo.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.aispeech.demo.AppCenter
import com.aispeech.framework.extensions.shortToast
import com.aispeech.framework.fast.FastViewModel
import com.aispeech.idds.IDDS
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by spq on 2021/12/15
 */
private const val TAG = "TestViewModel"

@HiltViewModel
class TestViewModel @Inject constructor(
    @ApplicationContext application: Context,
) : FastViewModel(application as Application) {
    fun initDDS() {
        AppCenter.getDDSEntryPoint(context).getDDS().initDDS()
    }


}