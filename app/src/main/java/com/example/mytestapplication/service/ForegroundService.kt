package com.example.mytestapplication.service

import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.*
import android.graphics.BitmapFactory
import android.os.IBinder
import android.widget.RemoteViews
import com.example.mytestapplication.R
import com.example.mytestapplication.notification.ForegroundNF
import com.example.mytestapplication.widget.remote.UCFridayWidget
import com.example.mytestapplication.widget.remote.UCRandomEatWidget
import com.example.mytestapplication.widget.remote.UCTimeWidget
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ForegroundService : Service() {
    private val TAG = "ForegroundService"
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

    var arrayOfBKF = mutableListOf("豆浆油条","灌汤小笼包","葱油拌面","肉包","白粥咸菜","干拌面","酱香饼","小馄饨","粢米饭团","烧饼")
    var arrayOfLaunch = mutableListOf("麻辣烫","水饺","炸酱面","地锅鸡","水煮鱼","番茄炒蛋盖浇饭","猪排饭")

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private val mForegroundNF:ForegroundNF by lazy {
        ForegroundNF(this)
    }

    override fun onCreate() {
        super.onCreate()
        mForegroundNF.startForegroundNotification()
        intentFilter = IntentFilter()
        intentFilter!!.addAction(Intent.ACTION_TIME_TICK) //每分钟变化
        intentFilter!!.addAction("com.example.mytestapplication.ACTION.CHANGE_RANDOM_EAT")

        intentFilter!!.addAction(Intent.ACTION_DATE_CHANGED)
        timeChangeReceiver = FridayTimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_NOT_STICKY
        }
//        mForegroundNF.startForegroundNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mForegroundNF.stopForegroundNotification()
        unregisterReceiver(timeChangeReceiver)
        super.onDestroy()
    }

    //时间变化接收
    inner class FridayTimeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {


            when (intent.action) {
                Intent.ACTION_TIME_TICK, Intent.ACTION_TIME_CHANGED -> {
                    val calendar = Calendar.getInstance()
                    calendar.time = Date()
                    val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
                    awm = AppWidgetManager.getInstance(context)
                    //2.获取组件的名称
                    if (provider == null) {
                        provider = ComponentName(context, UCFridayWidget::class.java)
                    }

                    if (views == null) {
                        views = RemoteViews(context.packageName, R.layout.widget_layout_friday)
                    }
                    /***************更新 时间组件*******************/
                    var timeClockProvider = ComponentName(context, UCTimeWidget::class.java)
                    var timeClockViews =
                        RemoteViews(context.packageName, R.layout.widget_layout_tomeclock)
                    var timeHour  = GregorianCalendar()[Calendar.HOUR_OF_DAY]
                    var timeMinute = GregorianCalendar()[Calendar.MINUTE]
                    timeClockViews!!.setTextViewText(R.id.tv_hour,if(timeHour < 10) "0".plus(timeHour.toString()) else timeHour.toString())
                    timeClockViews!!.setTextViewText(R.id.tv_minute,if(timeMinute < 10) "0".plus(timeMinute.toString()) else timeMinute.toString())
                    timeClockViews!!.setTextViewText(R.id.tv_am_pm,if(timeHour<12) "Am" else "Pm")
                    awm!!.updateAppWidget(timeClockProvider, timeClockViews)
                    /*****************更新 结束***********************/

                    /****************更新 吃啥*****************/
                    var randomEatProvider = ComponentName(context,UCRandomEatWidget::class.java)
                    var randomEatViews = RemoteViews(context.packageName,R.layout.widget_layout_random_eat)
                    var intentEat = Intent()
                    intentEat.action = "com.example.mytestapplication.ACTION.CHANGE_RANDOM_EAT"
                    var PendIntent = PendingIntent.getBroadcast(context,
                        0, intentEat, 0)
                    randomEatViews!!.setOnClickPendingIntent(R.id.btn_change,PendIntent)
                    awm!!.updateAppWidget(randomEatProvider,randomEatViews)
                    /****************更新 吃啥 结束*****************/




                    isFriday = if (dayOfWeek - 1 == 5) true else false
                    currentDay = dayOfWeek - 1
                    if (currentDay == lastDay) {
                        return
                    }
                    strDayOfWeek = JudgeFriday(context, dayOfWeek)


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
                    return
                }
                "com.example.mytestapplication.ACTION.CHANGE_RANDOM_EAT"-> {
                    awm = AppWidgetManager.getInstance(context)
                    var randomEatProvider = ComponentName(context,UCRandomEatWidget::class.java)
                    var randomEatViews = RemoteViews(context.packageName,R.layout.widget_layout_random_eat)
                    var result = getRandomWithSize(arrayOfBKF);
                    randomEatViews!!.setTextViewText(R.id.tv_title_eat_what, result)
                    var intentEat = Intent()
                    intentEat.action = "com.example.mytestapplication.ACTION.CHANGE_RANDOM_EAT"
                    var PendIntent = PendingIntent.getBroadcast(context,
                        0, intentEat, 0)
                    randomEatViews!!.setOnClickPendingIntent(R.id.btn_change,PendIntent)
                    awm!!.updateAppWidget(randomEatProvider, randomEatViews)
                    return
                }
                else -> {}

            }
        }

    }


    fun getRandomWithSize(list:MutableList<String>) : String {
        var size = list.size
        var index = (0 until size).random().toInt()
        return list.get(index = index);
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