package com.example.mytestapplication.module.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapplication.R
import com.gyf.immersionbar.ktx.immersionBar

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            fullScreen(true)
        }
        setContentView(layoutId())
        initView()
        initData()
    }

    abstract fun layoutId() : Int
    abstract fun initView()
    abstract fun initData()
}