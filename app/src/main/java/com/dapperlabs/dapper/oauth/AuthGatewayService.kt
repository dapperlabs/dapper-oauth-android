package com.dapperlabs.dapper.oauth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthGatewayService {

    @GET("auth-gateway/token")
    fun getTokens(@Query("ott") ott: String): Call<TokensResponse>
}