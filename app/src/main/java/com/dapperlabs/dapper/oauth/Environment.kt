package com.dapperlabs.dapper.oauth

enum class Environment {
    STAGING {
        override val hydraAuthUrl: String = "https://api.staging.app.dapperlabs.com/hydra/oauth2/auth"
        override val gatewayAuthUrl: String = "https://api.staging.nba.dapperlabs.com/auth-gateway/auth"
        override val tokenUrl: String = "https://api.staging.nba.dapperlabs.com/"
    },

    PRODUCTION {
        override val hydraAuthUrl: String = "https://api.app.dapperlabs.com/hydra/oauth2/auth"
        override val gatewayAuthUrl: String = "https://api.nba.dapperlabs.com/auth-gateway/auth"
        override val tokenUrl: String = "https://api.nba.dapperlabs.com/"
    };

    abstract val hydraAuthUrl: String
    abstract val gatewayAuthUrl: String
    abstract val tokenUrl: String
}