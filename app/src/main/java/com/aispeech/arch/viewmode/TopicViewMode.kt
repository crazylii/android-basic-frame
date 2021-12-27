package com.aispeech.arch.viewmode

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.aispeech.arch.UID
import com.aispeech.framework.fast.FastViewModel
import com.aispeech.arch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class TopicViewMode @Inject constructor(
    @ApplicationContext context: Context
) : FastViewModel(context as Application){
    @Inject lateinit var mainRepository : MainRepository
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadData(topicName: String) {
        mainRepository.getTopicList(UID, topicName)
    }
}