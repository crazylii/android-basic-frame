package com.yollpoll.arch.router;

import android.content.Context;

import java.util.HashMap;

public interface OnBackListener {
    void  onBack(HashMap<String, String> param, Context context);
}
