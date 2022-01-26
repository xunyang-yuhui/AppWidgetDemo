package com.example.mytestapplication

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mytestapplication.module.base.BaseActivity
import com.example.mytestapplication.service.ForegroundService
import com.example.mytestapplication.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class MainActivity : BaseActivity() {
    private lateinit var service:ForegroundService;
    private lateinit var iv_setting:ImageView
    private lateinit var iv_random_eat:ImageView

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        iv_setting = findViewById(R.id.iv_setting)
        iv_random_eat = findViewById(R.id.iv_random_eat)

        iv_setting.setOnClickListener {
            Navigation.gotoSetting(baseContext)
        }

        iv_random_eat.setOnClickListener {
            Navigation.gotoRandomEat(baseContext)
        }
    }

    override fun initData() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:$packageName")
        //启动忽略电池优化，会弹出一个系统的弹框，我们在上面的

        val mIgnoreBatteryResultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            //查询是否开启成功
            if(queryBatteryOptimizeStatus()){
                //忽略电池优化开启成功
            }else{
                //开启失败
            }
        }

        startService(Intent(this,ForegroundService::class.java))
    }


    override fun finish() {
        super.finish()
        stopService(Intent(this,ForegroundService::class.java))
    }



    fun Context.queryBatteryOptimizeStatus():Boolean{
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager?
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            powerManager?.isIgnoringBatteryOptimizations(packageName)?:false
        } else {
            true
        }
    }

}