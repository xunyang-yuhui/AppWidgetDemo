package com.example.mytestapplication.widget.remote

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.example.mytestapplication.R

class UCRandomEatWidget : AppWidgetProvider() {
    private val ACTION_CHANGE_RANDOM_EAT = "com.example.mytestapplication.ACTION.CHANGE_RANDOM_EAT";
    private val TAG = "UCRandomEatWidget"

    override fun onReceive(context: Context?, intent: Intent?) {
        var action = intent?.action;
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        var remoteVies = RemoteViews(context!!.packageName, R.layout.widget_layout_random_eat)
        var intentEat = Intent()
        intentEat.action = "com.example.mytestapplication.ACTION.CHANGE_RANDOM_EAT"
        var PendIntent = PendingIntent.getBroadcast(context,
            0, intentEat, 0)
        remoteVies!!.setOnClickPendingIntent(R.id.btn_change,PendIntent)
        appWidgetManager!!.updateAppWidget(appWidgetIds,remoteVies)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }
}