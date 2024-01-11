package com.bsoftware.setara.sharePreference

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class VideoIdSharePref(val activity : Activity) {
    private val videoIdSharePref : SharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val videoIdEdit = videoIdSharePref.edit()

    fun setVideoId(videoId : String){
        videoIdEdit.apply {
            putString(VIDEO_ID,videoId)
            commit()
        }
    }

    fun getVideoId() : String? {
        return videoIdSharePref.getString(VIDEO_ID,"Video1")
    }

    companion object{
        private val PREF_NAME : String = "VideoIdSharePrefData"
        private val VIDEO_ID : String = "VideoIdKey"
    }
}