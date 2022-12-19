package com.sweet.sign

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

class SignMain {

    fun uploadSign(file: File,url:String){
        val pMap = HashMap<String,Any?>()
        val body = buildMultipartBody(pMap)
        val fileRequestBody = FileRequestBody(body, object : ProgressCallback() {
            override fun onLoading(total: Long, progress: Long) {
                //上传进度
            }
        })
        val okHttpClient = OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val req = Request.Builder().url(url).post(fileRequestBody).build()
        okHttpClient.newCall(req).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
        })

    }
    private fun buildMultipartBody(pMap: Map<String, Any?>): MultipartBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val keySet = pMap.keys
        for (key in keySet) {
            val value = pMap[key]
            if (value is File) {
                val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), value)
                val encode = URLEncoder.encode(value.name)
                builder.addFormDataPart(key, encode, requestBody)
            } else if (value is String) {
                val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), value)
                builder.addFormDataPart(key, null, requestBody)
            } else if (value is Int) {
                builder.addFormDataPart(key, value.toString())
            } else {
                if (value == null) {
//                    builder.addFormDataPart(key, "")
                } else {
                    builder.addFormDataPart(key, value.toString())
                }
            }
        }
        return builder.build()
    }
}