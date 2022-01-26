package com.example.mytestapplication.utils

import android.app.ActivityManager
import android.content.Context

class Utils private constructor() {
    companion object {

        fun hideAppWindow(context: Context, isHide:Boolean){
            try {
                val activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                //控制App的窗口是否在多任务列表显示
                activityManager.appTasks[0].setExcludeFromRecents(isHide)
            }catch (e:Exception){

            }
        }
    }



}