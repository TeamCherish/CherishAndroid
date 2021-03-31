package com.sopt.cherish.util

import com.sopt.cherish.remote.singleton.RetrofitBuilder
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

object ErrorUtils {
    fun parseError(response: Response<*>): APIError {
        val converter: Converter<ResponseBody, APIError> =RetrofitBuilder.getRetrofit()
            .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))

        var error: APIError?=null

        error = try {
            converter.convert(response.errorBody())
        } catch (e: IOException) {
            return APIError()
        }
        return error!!
    }
}