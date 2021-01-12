package com.sopt.cherish.util.extension

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitExtension {
    fun <ResponseType> Call<ResponseType>.customEnqueue(
        onSuccess: (ResponseType) -> Unit,
        onError: (ResponseBody) -> Unit = {}
    ) {
        this.enqueue(object : Callback<ResponseType> {
            override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
                // 성공

            }

            override fun onFailure(call: Call<ResponseType>, t: Throwable) {
                // 실패
            }
        })
    }
}