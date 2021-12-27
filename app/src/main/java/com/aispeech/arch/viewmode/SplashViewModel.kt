package com.aispeech.arch.viewmode

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.aispeech.arch.UID
import com.aispeech.framework.fast.FastViewModel
import com.aispeech.arch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext context: Context
) : FastViewModel(context as Application){
    @Inject lateinit var mainRepository : MainRepository
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadData() {
        viewModelScope.launch() {
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let {
                MainRepository().getSplashBanner(UID,
                    it
                )
            }
        }
    }
}