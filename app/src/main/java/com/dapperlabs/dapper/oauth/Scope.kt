package com.dapperlabs.dapper.oauth
/**
 * OAuth scopes that allow clients to read or write certain user data on behalf of the user
 */

enum class Scope(val scope: String) {

    /**
     *  If granted, the client will receive an ID token along with the access token in response to a valid request
     *  to fetch tokens from Hydra's token endpoint.
     */
    OPEN_ID("openid"),

    /**
     * If granted, the client will receive a refresh token along with the access token in response to a valid request
     * to fetch tokens from Hydra's token endpoint.
     */
    OFFLINE ("offline"),

    /**
     * Allows the client to fetch the user's emails from Identity using `GET /oauth/users/email` with a valid access token.
     */
    EMAIL ("email"),

    /**
     * Allows the client to fetch the user's profile information (username and avatar image URL) from Identity
     * using `GET /oauth/users/profile` with a valid access token.
     */
    PROFILE ("profile"),

    /**
     * Allows the client to fetch the user's Ethereum wallet address from Identity using `GET /oauth/users/ethereum/wallet-address`
     * with a valid access token.
     */
    READ_WALLET_ADDRESS ("app.dapperlabs.com/eth.wallet.read"),

    /**
     * Allows the client to fetch the user's Flow account ID from Identity using `GET /oauth/users/flow/wallet-address`
     * with a valid access token.
     */
    READ_FLOW_ADDRESS ("app.dapperlabs.com/flow.wallet.read"),

    /**
     * Allows the client to send  Flow transactions on the user's behalf.
     */
    INVOKE  ("app.dapperlabs.com/flow.wallet.invoke")
}


