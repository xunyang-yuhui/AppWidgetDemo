package com.example.mytestapplication.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * mmkv
 */
object KVUtils {
    var mv:MMKV? = null
    init {
        mv = MMKV.defaultMMKV()
    }

    fun encode (key:String?,value:Any) {
        when (value) {
            is String ->{
                mv?.encode(key,value)
            }
            is Int -> {
                mv?.encode(key,value)
            }
            is Boolean ->{
                mv?.encode(key,value)
            }
            is Float ->{
                mv?.encode(key,value);
            }
            is Long ->{
                mv?.encode(key,value)
            }
            is Double ->{
                mv?.encode(key, value)
            }
            is ByteArray ->{
                mv?.encode(key, value)
            }
            else ->{
                mv?.encode(key,value.toString())
            }
        }
    }

    fun encodeSet(key:String?,sets:Set<String>?) {
        mv?.encode(key,sets)
    }

    fun encodeParcelable(key:String?,obj:Parcelable?) {
        mv?.encode(key,obj)
    }

    fun decodeInt(key: String?):Int? {
        return mv?.decodeInt(key,0)
    }

    fun decodeDouble(key: String?):Double{
        return mv?.decodeDouble(key,0.00)!!
    }

    fun decodeLong(key: String?):Long {
        return mv?.decodeLong(key,0L)!!
    }

    fun decodeBoolean(key: String?):Boolean {
        return mv?.decodeBool(key,false) == true
    }

    fun decodeBooleanTrue(key: String?,defaultValue:Boolean):Boolean{
        return mv?.decodeBool(key,defaultValue) == true
    }

    fun decodeFloat(key: String?):Float {
        return mv?.decodeFloat(key,0f)!!
    }

    fun decodeBytes(key: String?):ByteArray {
        return mv?.decodeBytes(key)!!
    }

    fun decodeString(key: String?):String {
        return mv?.decodeString(key,"").toString()
    }

    fun decodeStringDef(key: String?,defaultValue:String):String {
        return mv?.decodeString(key,defaultValue).toString()
    }

    fun decodeStringSet(key: String?):Set<String> {
        return mv?.decodeStringSet(key, emptySet()) as Set<String>
    }

    fun <T:Parcelable?> decodeParcelable(key: String?,tClass:Class<T>?):T? {
        return mv?.decodeParcelable(key,tClass)
    }

    fun removeKey(key: String?) {
        mv?.removeValueForKey(key)
    }

    fun clearAll(){
        mv?.clearAll()
    }
}