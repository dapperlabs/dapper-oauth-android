package com.dapperlabs.dapper.oauth

interface DapperAuthListener {

    fun onAuthenticationSuccess(tokens: TokensResponse)
    fun onAuthenticationFailure(error: String)
}