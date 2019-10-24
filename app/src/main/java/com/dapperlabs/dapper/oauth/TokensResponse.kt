package com.dapperlabs.dapper.oauth

data class TokensResponse(
    val sessionToken: String,
    val csrfToken: String
)