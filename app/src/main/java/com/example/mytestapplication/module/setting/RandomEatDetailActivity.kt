package com.example.mytestapplication.module.setting

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapplication.R
import com.example.mytestapplication.module.base.BaseActivity

class RandomEatDetailActivity : BaseActivity() {
    private lateinit var iv_base_back:ImageView
    private lateinit var rcv_list:RecyclerView

    override fun layoutId(): Int {
        return R.layout.activity_randomeat_detail
    }

    override fun initView() {
        iv_base_back = findViewById(R.id.iv_base_back )
    }

    override fun initData() {
        iv_base_back.setOnClickListener {
            onBackPressed()
        }
    }
}