package com.aispeech.dds

import android.annotation.TargetApi
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import com.aispeech.DUILiteSDK
import com.aispeech.dds.observer.ObserverManager
import com.aispeech.dui.dds.DDS
import com.aispeech.dui.dds.DDSAuthListener
import com.aispeech.dui.dds.DDSInitListener
import com.aispeech.idds.*
import com.yollpoll.arch.message.MessageManager
import com.yollpoll.arch.util.ToastUtil

/**
 * Created by spq on 2021/12/14
 */
class DDSService : Service() {
    companion object {
        const val TAG = "DdsService"
        private var isStarted = false
        fun start(context: Context) {
            context.startService(newDDSServiceIntent(context, "start"))
        }

        fun stop(context: Context) {
            context.startService(newDDSServiceIntent(context, "stop"))
        }

        private fun newDDSServiceIntent(context: Context, action: String): Intent {
            val intent = Intent(context, DDSService::class.java)
            intent.action = action
            return intent
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }


    override fun onCreate() {
        setForeground()
        isStarted = false
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            Log.i(TAG, "action:$action")
            if (TextUtils.equals(action, "start")) {
                if (isStarted) {
                    Log.i(TAG, "already started")
                    return super.onStartCommand(intent, flags, startId)
                }
                init()
                isStarted = true
            } else if (TextUtils.equals(action, "stop")) {
                //??????timerstopRefreshTokenTimer()
                if (!isStarted) {
                    Log.i(TAG, "already stopped")
                    return super.onStartCommand(intent, flags, startId)
                }
                isStarted = false
                DDS.getInstance().releaseSync()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * ?????????dds
     */
    fun init() {
//        DDS.getInstance().setDebugMode(2) //????????????????????????sdk?????????????????????????????????????????????
        DDS.getInstance().init(applicationContext, config, object : DDSInitListener {
            override fun onInitComplete(isFull: Boolean) {
                Log.d(TAG, "onInitComplete: $isFull")
                if (isFull) {
                    MessageManager.getInstance().sendEmptyMessage(DDS_MSG_INIT_COMPLETE)
                    if (DDS.getInstance().isAuthSuccess) {
                        //????????????
                        MessageManager.getInstance().sendEmptyMessage(DDS_MSG_AUTH_SUCCESS)
                        MessageManager.getInstance().sendEmptyMessage(DDS_MSG_INIT_FINISH)
                        ObserverManager.registerAll()
                    } else {
                        //????????????
                        DDS.getInstance().doAuth()
                    }
                } else {
                    //????????????????????????????????????
                    MessageManager.getInstance().sendEmptyMessage(DDS_MSG_INIT_INCOMPLETE)
                }
            }

            override fun onError(what: Int, msg: String?) {
                Log.d(TAG, "onError: ???????????????:$msg")
                MessageManager.getInstance().sendEmptyMessage(DDS_MSG_INIT_ERROR)
            }
        }, object : DDSAuthListener {
            override fun onAuthSuccess() {
                Log.d(TAG, "onAuthSuccess: ????????????")
                MessageManager.getInstance().sendEmptyMessage(DDS_MSG_AUTH_SUCCESS)
                MessageManager.getInstance().sendEmptyMessage(DDS_MSG_INIT_FINISH)
                ObserverManager.registerAll()
            }

            override fun onAuthFailed(errorId: String?, error: String?) {
                ToastUtil.showShortToast("DDS????????????:${error} ????????? $errorId")
                MessageManager.getInstance().sendEmptyMessage(DDS_MSG_AUTH_FAILED)
            }
        })
    }


    @TargetApi(Build.VERSION_CODES.O)
    private fun setForeground() {
        val intent = Intent(this@DDSService, DDsActivity::class.java)
        val pi = PendingIntent.getActivity(this@DDSService, 0, intent, 0)
        val notification: Notification = Util.pupNotification(this@DDSService, pi, "DDS?????????...")
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        // ?????????app??????dds????????????
        isStarted = false
        ObserverManager.unRegisterAll()
        DDS.getInstance().releaseSync()
    }
}