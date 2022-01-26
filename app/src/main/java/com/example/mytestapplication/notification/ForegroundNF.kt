package com.example.mytestapplication.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.mytestapplication.MainActivity
import com.example.mytestapplication.R
import com.example.mytestapplication.service.ForegroundService

/**
 * 前台通知
 */
class ForegroundNF(private val service: ForegroundService) : ContextWrapper(service) {
    companion object {
        private const val START_ID = 101
        private const val CHANNEL_ID = "app_foreground_service"
        private const val CHANNEL_NAME = "前台保活服务"
    }

    private var mNotificationManager : NotificationManager? = null
    private var mCompatBuilder : NotificationCompat.Builder? = null
    private val compatBuilder : NotificationCompat.Builder?
        get() {
            if (mCompatBuilder == null) {
                val notificationIntent = Intent(this,MainActivity::class.java)
                notificationIntent.action = Intent.ACTION_MAIN
                notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED

                val pendingIntent = PendingIntent.getActivity(this,(Math.random()*10 +10).toInt(),notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT)
                val notificationBuilder:NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
                notificationBuilder.setContentTitle(getString(R.string.str_notification_title))
                notificationBuilder.setContentText(getString(R.string.str_notification_content))
                notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                notificationBuilder.setContentIntent(pendingIntent)
                mCompatBuilder = notificationBuilder

            }
            return mCompatBuilder
        }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //针对8.0以上系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_LOW)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.setShowBadge(false)
            mNotificationManager?.createNotificationChannel(channel)
        }
    }

    //开启前台服务
    fun startForegroundNotification() {
        service.startForeground(START_ID,compatBuilder?.build())
    }

    //停止前台服务
    fun stopForegroundNotification() {
        mNotificationManager?.cancelAll()
        service.stopForeground(true)
    }

}