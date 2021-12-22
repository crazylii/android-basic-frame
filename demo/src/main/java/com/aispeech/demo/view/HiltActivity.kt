package com.aispeech.demo.view

import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.aispeech.framework.fast.FastActivity
import com.aispeech.framework.fast.FastViewModel

/**
 * Created by spq on 2021/12/15
 */
class HiltActivity<BIND : ViewDataBinding, VM : FastViewModel> : FastActivity<BIND, VM>() {

}