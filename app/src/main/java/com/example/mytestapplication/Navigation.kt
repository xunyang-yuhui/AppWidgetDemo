package com.example.mytestapplication

import android.content.Context
import android.content.Intent
import com.example.mytestapplication.module.setting.RandomEatDetailActivity
import com.example.mytestapplication.module.setting.RandomEatSettingActivity
import com.example.mytestapplication.module.setting.SettingActivity
import com.example.mytestapplication.utils.Constanct

class Navigation {
    companion object {
        fun gotoSetting(context: Context) {
            var intent = Intent(context,SettingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun gotoRandomEat(context: Context) {
            var intent = Intent(context,RandomEatSettingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun gotoRandomEatDetail(context: Context,type:Int) {
            var intent = Intent(context,RandomEatDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Constanct.EXTRA_FROM,type)
            context.startActivity(intent)
        }
    }
}