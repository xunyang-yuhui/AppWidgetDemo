package com.example.mytestapplication.module.setting

import android.widget.ImageView
import android.widget.TextView
import com.example.mytestapplication.R
import com.example.mytestapplication.module.base.BaseActivity
import com.example.mytestapplication.utils.Constanct
import com.example.mytestapplication.utils.KVUtils
import com.example.mytestapplication.utils.Utils
import com.tencent.mmkv.MMKV

class SettingActivity : BaseActivity() {
    private lateinit var iv_base_back:ImageView
    private lateinit var tv_base_title:TextView
    private lateinit var iv_switch_task:ImageView

    override fun layoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        iv_base_back = findViewById(R.id.iv_base_back)
        tv_base_title = findViewById(R.id.tv_base_title)
        iv_switch_task = findViewById(R.id.iv_switch_task)
    }

    override fun initData() {
        var isOn = KVUtils.decodeBooleanTrue(Constanct.MMKV_KEY_SWITCH,true)
        iv_switch_task.setImageResource(if(isOn) R.drawable.ic_switch_on else R.drawable.ic_switch_off)

        iv_base_back.setOnClickListener {
            onBackPressed()
        }

        tv_base_title.text = "设置"

        iv_switch_task.setOnClickListener {
            if (isOn) {
                isOn = false;
                iv_switch_task.setImageResource(R.drawable.ic_switch_off)

            } else {
                isOn = true;
                iv_switch_task.setImageResource(R.drawable.ic_switch_on)
            }

            MMKV.defaultMMKV().encode(Constanct.MMKV_KEY_SWITCH,isOn)
            Utils.hideAppWindow(baseContext,isOn)
        }
    }
}