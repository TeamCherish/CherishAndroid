package com.sopt.cherish.util.extension

import android.content.Context
import android.content.SharedPreferences

object ImageSharedPreferences {
    private val MY_ACCOUNT:String="account"

    fun setCameraFile(context: Context, input: String){
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=prefs.edit()
        editor.putString("camera",input)
        editor.apply()
    }

    fun getCameraFile(context:Context):String{
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        return prefs.getString("camera","").toString()
    }

    fun setGalleryFile(context: Context, input:String){
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=prefs.edit()
        editor.putString("gallery",input)
        editor.apply()
    }

    fun getGalleryFile(context:Context):String{
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        return prefs.getString("gallery","").toString()
    }

    fun clearImage(context:Context,key:String){
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=prefs.edit()
        editor.remove(key)
        editor.apply()
    }
}