package com.example.mytestapplication.module.setting.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SettingChooseAdapter(var context:Context,var list:MutableList<String>) :
    RecyclerView.Adapter<SettingChooseAdapter.SettingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingVH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SettingVH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SettingVH(itemView : View) : RecyclerView.ViewHolder(itemView){

    }
}