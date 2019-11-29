package com.dapperlabs.dapper.oauth

import android.content.Context
import android.content.Intent
import android.net.Uri

const val CLIENT_ID = "client_id"
const val RESPONSE_TYPE = "response_type"
const val REDIRECT_URI = "redirect_uri"
const val STATE = "state"
const val SCOPE = "scope"

class DapperAuthClient(private val context: Context, private val clientId: String, private val clientRedirectUri:String, private val environment: Environment) {

    companion object {
        internal lateinit var dapperAuthListener: DapperAuthListener
        internal lateinit var environment: Environment
    }

    init {
        Companion.environment = environment
    }

    /**
     *
     * @param scopes Scope that allow clients to read or write certain user data on behalf of the user
     * @param dapperAuthListener A callback listener for authentication result
     */

    fun login(
        scopes: List<Scope> = listOf(
            Scope.OPEN_ID,
            Scope.EMAIL,
            Scope.PROFILE,
            Scope.OFFLINE,
            Scope.READ_WALLET_ADDRESS
        ),
        dapperAuthListener: DapperAuthListener
    ) {
        Companion.dapperAuthListener = dapperAuthListener
        val intent = Intent(Intent.ACTION_VIEW, buildAuthUrl(scopes))
        context.startActivity(intent)
    }


    private fun buildAuthUrl(scopes: List<Scope>): Uri {
        return Uri.parse(environment.hydraAuthUrl)
            .buildUpon()
            .appendQueryParameter(CLIENT_ID, clientId)
            .appendQueryParameter(STATE, "{\"redirectUrl\":\"$clientRedirectUri\"}")
            .appendQueryParameter(RESPONSE_TYPE, "code")
            .appendQueryParameter(SCOPE, scopes.joinToString(separator = " ") { it.scope })
            .appendQueryParameter(REDIRECT_URI, environment.gatewayAuthUrl)
            .build()
    }
}