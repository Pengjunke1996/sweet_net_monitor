package com.sweet.net_monitor.net.mock

import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import java.nio.charset.Charset

object MockHelper {

    var isOpen = false

    /**
     * mock服务的baseUrl  例如PostMan的mock服务：https://881adba2-445e-4ad2-8633-3206a2b0ef94.mock.pstmn.io
     */
    var mockBaseUrl: String = ""

    /**
     * 需要mock的接口path  例如：/api/spero-ultron-service/room/400/recommendAuthor
     */
    var mockPaths: String = ""

    /**
     * 手动创建的response
     */
    var mockResponse: String = ""

    /**
     * 请求成功的code
     */
    var mockSuccessResponseCode = 200

    fun isMockPath(path: String?): Boolean {
        if (path.isNullOrBlank()) return false
        return mockPaths.contains(path)
    }

    fun isMockByCustomResponse(path: String?): Boolean {
        return isOpen && isMockPath(path) && mockResponse.isNotBlank()
    }

    fun isMockByNetworkResponse(path: String?): Boolean {
        return isOpen && isMockPath(path) && mockBaseUrl.isNotBlank() && mockResponse.isBlank()
    }

    fun configMock(mockBaseUrl: String, mockPath: String, mockResponse: String) {
        MockHelper.mockBaseUrl = mockBaseUrl
        mockPaths = mockPath
        MockHelper.mockResponse = mockResponse
    }

    fun buildMockServer(chain: Interceptor.Chain, request: Request): Response {
        val oldHttpUrl = request.url()
        val newBaseUrl = HttpUrl.parse(mockBaseUrl) ?: return chain.proceed(request)
        val newHttpUrl = oldHttpUrl
            .newBuilder()
            .scheme(newBaseUrl.scheme())
            .host(newBaseUrl.host())
            .port(newBaseUrl.port())
            .build()
        return chain.proceed(request.newBuilder().url(newHttpUrl).build())
    }

    fun mockResponseBody(chain: Interceptor.Chain): Response {
        if (mockResponse.isBlank()) return chain.proceed(chain.request())
        val response = chain.proceed(chain.request())
        val contentType = response.body()?.contentType()

        val responseBody = mockResponse.toResponseBody(contentType)
        return response.newBuilder()
            .message("mock成功")
            .code(mockSuccessResponseCode)
            .body(responseBody)
            .build()
    }

    @JvmStatic
    @JvmName("create")
    fun String.toResponseBody(contentType: MediaType? = null): ResponseBody {
        var charset: Charset = Charsets.UTF_8
        var finalContentType: MediaType? = contentType
        if (contentType != null) {
            val resolvedCharset = contentType.charset()
            if (resolvedCharset == null) {
                charset = Charsets.UTF_8
                finalContentType = MediaType.parse("$contentType; charset=utf-8")
            } else {
                charset = resolvedCharset
            }
        }
        val buffer = Buffer().writeString(this, charset)
        return buffer.asResponseBody(finalContentType, buffer.size())
    }

    @JvmStatic
    @JvmName("create")
    fun BufferedSource.asResponseBody(
        contentType: MediaType? = null,
        contentLength: Long = -1L
    ): ResponseBody = object : ResponseBody() {
        override fun contentType() = contentType

        override fun contentLength() = contentLength

        override fun source() = this@asResponseBody
    }
}