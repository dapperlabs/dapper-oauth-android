package com.dapperlabs.dapper.oauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RedirectUriReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Deeplink callback intercepted
        intent?.data?.let {
            it.getQueryParameter("error")?.let { _ ->
                DapperAuthClient.dapperAuthListener.onAuthenticationFailure(it.getQueryParameter("error_description") ?: "Something is wrong")
                finish()
            } ?: kotlin.run {
                it.getQueryParameter("ott")?.let { oneTimeToken ->
                    getAuthGatewayService().getTokens(oneTimeToken)
                        .enqueue(object : Callback<TokensResponse> {
                            override fun onFailure(call: Call<TokensResponse>, t: Throwable) {
                                DapperAuthClient.dapperAuthListener.onAuthenticationFailure("Failed to authenticate")
                                finish()
                            }

                            override fun onResponse(
                                call: Call<TokensResponse>,
                                response: Response<TokensResponse>
                            ) {
                                response.body()?.let {
                                    DapperAuthClient.dapperAuthListener.onAuthenticationSuccess(it)

                                } ?: kotlin.run {
                                    DapperAuthClient.dapperAuthListener.onAuthenticationFailure("Failed to authenticate")
                                }
                                finish()
                            }

                        })
                } ?: kotlin.run {
                    DapperAuthClient.dapperAuthListener.onAuthenticationFailure("Failed to authenticate")
                    finish()
                }
            }
        }
    }

    private fun getAuthGatewayService(): AuthGatewayService {
        val retrofit = Retrofit.Builder()
            .baseUrl(DapperAuthClient.environment.tokenUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(AuthGatewayService::class.java)
    }
}
