package com.sopt.cherish.ui.main.setting

import android.content.Context
import android.content.SharedPreferences

object ImageSharedPreferences {
    private val MY_ACCOUNT:String="account"

    fun setImageFile(context: Context, input:String){
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=prefs.edit()
        editor.putString("image",input)
        editor.commit()
    }

    fun getImageFile(context:Context):String{
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        return prefs.getString("image","").toString()
    }

    fun clearImage(context:Context){
        val prefs:SharedPreferences=context.getSharedPreferences(MY_ACCOUNT,Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=prefs.edit()
        editor.clear()
        editor.commit()
    }
}