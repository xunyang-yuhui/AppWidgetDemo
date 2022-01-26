package com.example.mytestapplication.module.setting

import android.widget.ImageView
import com.example.mytestapplication.Navigation
import com.example.mytestapplication.R
import com.example.mytestapplication.module.base.BaseActivity
import com.example.mytestapplication.utils.Constanct

class RandomEatSettingActivity : BaseActivity() {
    private lateinit var iv_base_back:ImageView
    private lateinit var iv_breakfast:ImageView
    private lateinit var iv_launch:ImageView
    private lateinit var iv_dinner:ImageView
    private lateinit var iv_yexiao:ImageView


    override fun layoutId(): Int {
        return R.layout.activity_randomeat_setting
    }

    override fun initView() {
        iv_base_back = findViewById(R.id.iv_base_back)
        iv_breakfast = findViewById(R.id.iv_breakfast)
        iv_launch = findViewById(R.id.iv_launch)
        iv_dinner = findViewById(R.id.iv_dinner)
        iv_yexiao = findViewById(R.id.iv_yexiao)

    }

    override fun initData() {
        iv_base_back.setOnClickListener {
            onBackPressed()
        }

        iv_breakfast.setOnClickListener {
            Navigation.gotoRandomEatDetail(baseContext,Constanct.CONSTANCT_0)
        }

        iv_launch.setOnClickListener {
            Navigation.gotoRandomEatDetail(baseContext,Constanct.CONSTANCT_1)
        }

        iv_dinner.setOnClickListener {
            Navigation.gotoRandomEatDetail(baseContext,Constanct.CONSTANCT_2)
        }

        iv_yexiao.setOnClickListener {
            Navigation.gotoRandomEatDetail(baseContext,Constanct.CONSTANCT_3)
        }
    }
}