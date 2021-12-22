package com.aispeech.idds

/**
 * Created by spq on 2021/12/16
 */
const val SYS_ACTION_CALL: String = "sys.action.call"

private const val COMMAND_OPEN = "sys.action.open"
private const val COMMAND_PLAY = "sys.action.play"

//媒体播放控制
const val MEDIA_PLAY = "DUI.MediaController.Play" //开始播放

const val MEDIA_PAUSE = "DUI.MediaController.Pause" //暂停播放

const val MEDIA_NEXT = "DUI.MediaController.Next" //下一首

const val MEDIA_PRE = "DUI.MediaController.Prev" //上一首

const val MEDIA_STOP = "DUI.MediaController.Stop" //停止播放

const val MEDIA_VOLUME = "DUI.MediaController.SetVolume" //音量控制

const val MEDIA_VOLUME_OPEN = "DUI.System.Sounds.OpenMode" //打开声音

const val MEDIA_VOLUME_CLOSE = "DUI.System.Sounds.CloseMode" //关闭声音


const val NAIGATION_SELECT_FOCUS = "DUI.UINavigationControler.SelectFocus" //页面切换控制


val commandList = arrayOf(
    COMMAND_OPEN,
    COMMAND_PLAY,
    MEDIA_PLAY,
    MEDIA_PAUSE,
    MEDIA_NEXT,
    MEDIA_PRE,
    MEDIA_STOP,
    MEDIA_VOLUME,
    MEDIA_VOLUME_OPEN,
    MEDIA_VOLUME_CLOSE,
    NAIGATION_SELECT_FOCUS
)