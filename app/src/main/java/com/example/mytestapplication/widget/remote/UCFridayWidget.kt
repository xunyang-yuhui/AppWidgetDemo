package com.example.mytestapplication.widget.remote

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.*
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.RemoteViews
import com.example.mytestapplication.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * 今天是星期五吗 组件
 */
class UCFridayWidget : AppWidgetProvider() {
     var intentFilter: IntentFilter? = null
     var timeChangeReceiver: FridayTimeChangeReceiver? = null
     var lastDay = -1
     var currentDay = -2
     var strDayOfWeek = ""
     var strOfDate = ""
     var isFriday = false
     var isHasSet = false
    var views: RemoteViews? = null
    var awm: AppWidgetManager? = null
    var provider: ComponentName? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        if (currentDay == lastDay) {
            return
        }
        val remoteViews = RemoteViews(context!!.packageName, R.layout.widget_layout_friday)

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        isFriday = if (dayOfWeek - 1 == 5) true else false
        currentDay = dayOfWeek - 1
        strDayOfWeek = JudgeFriday(context!!, dayOfWeek - 1)

        if (isFriday) {
            if (!isHasSet) {
                remoteViews.setImageViewBitmap(
                    R.id.iv_lihua,
                    BitmapFactory.decodeResource(context!!.resources, R.drawable.lihua)
                )
            }
            isHasSet = true
        } else {
            remoteViews.setImageViewBitmap(R.id.iv_lihua, null)
        }
        remoteViews.setTextViewText(R.id.tv_is_friday, strDayOfWeek)

        try {
            val format = SimpleDateFormat("yyyy年MM月dd日")
            val date = Date()
            strOfDate = format.format(date)
        } catch (e: Exception) {
        }
        appWidgetManager!!.updateAppWidget(appWidgetIds, remoteViews)
        remoteViews.setTextViewText(R.id.tv_title_date, strOfDate)
//        if(appWidgetManager != null) {
        //        if(appWidgetManager != null) {


//        }

//        }
        lastDay = currentDay
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        intentFilter = IntentFilter()
        intentFilter!!.addAction(Intent.ACTION_TIME_TICK) //每分钟变化

        intentFilter!!.addAction(Intent.ACTION_DATE_CHANGED)
        timeChangeReceiver = FridayTimeChangeReceiver()
        context!!.applicationContext.registerReceiver(timeChangeReceiver, intentFilter)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        context!!.applicationContext.unregisterReceiver(timeChangeReceiver)
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

    //时间变化接收
    inner class FridayTimeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_TIME_TICK, Intent.ACTION_TIME_CHANGED -> {
                    val calendar = Calendar.getInstance()
                    calendar.time = Date()
                    val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]

                    isFriday = if (dayOfWeek - 1 == 5) true else false
                    currentDay = dayOfWeek - 1
                    if (currentDay == lastDay) {
                        return
                    }
                    strDayOfWeek = JudgeFriday(context, dayOfWeek )
                    awm = AppWidgetManager.getInstance(context)
                    //2.获取组件的名称
                    if (provider == null) {
                        provider = ComponentName(context, UCFridayWidget::class.java)
                    }
                    if (views == null) {
                        views = RemoteViews(context.packageName, R.layout.widget_layout_friday)
                    }
                    if (isFriday) {
                        if (!isHasSet) {
                            views!!.setImageViewBitmap(
                                R.id.iv_lihua,
                                BitmapFactory.decodeResource(context.resources, R.drawable.lihua)
                            )
                        }
                        isHasSet = true
                    } else {
                        views!!.setImageViewBitmap(R.id.iv_lihua, null)
                    }


                    views!!.setTextViewText(R.id.tv_is_friday, strDayOfWeek)
                    try {
                        val format = SimpleDateFormat("yyyy年MM月dd日")
                        val date = Date()
                        strOfDate = format.format(date)
                    } catch (e: Exception) {
                    }
                    views!!.setTextViewText(R.id.tv_title_date, strOfDate)
                    awm!!.updateAppWidget(provider, views)

                    lastDay = currentDay
                }
                else -> {}
            }
        }
    }

    //格式化文本显示
    private fun JudgeFriday(context: Context, dayOfWeek: Int): String {
        var str = ""
        str =
            when (dayOfWeek) {
                0 -> String.format(context.getString(R.string.str_is_friday), "六")
                1 -> String.format(context.getString(R.string.str_is_friday), "五")
                2 -> String.format(context.getString(R.string.str_is_friday), "四")
                3 -> String.format(context.getString(R.string.str_is_friday), "三")
                4 -> String.format(context.getString(R.string.str_is_friday), "两")
                5 -> String.format(context.getString(R.string.str_is_friday), "一")
                else -> context.resources.getString(R.string.str_is_friday_yes)
            }
        return str
    }
}