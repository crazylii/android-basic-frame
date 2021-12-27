package com.aispeech.demo.viewmodel

import android.app.Application
import android.content.Context
import com.aispeech.demo.AppCenter
import com.aispeech.framework.fast.FastViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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