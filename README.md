# dapper-oauth-android
DapperOAuth is a lightweight SDK that allows clients to authenticate their users via Dapper's [OAuth2 Identity service](https://www.notion.so/dapperlabs/identity-api-b017d5851e5b4839b33f9dfb02278c2b) and the [NBA Auth Gateway]((https://www.notion.so/dapperlabs/NBA-Auth-Gateway-f0a4d607c9174edb99a2b0574d8bbf9a)).

## Authentication Flow

The purpose of this SDK is to provide clients with a simple way to authenticate users through Dapper's OAuth2 provider, the Identity service. Once the user has successfully authenticated with their Dapper credentials, the SDK will return the user's session token and CSRF token to the client application, allowing it to make authenticated requests on behalf of the user.


A more detailed explanation of the OAuth flow can be found [here](https://www.notion.so/dapperlabs/NBA-Auth-Gateway-f0a4d607c9174edb99a2b0574d8bbf9a). At a high level, the SDK performs the following steps:   

1. Loads the Dapper login page using `SFAuthenticationSession` at the following URL:

```
"https://app.dapperlabs.com/hydra/oauth2/auth \
	?client_id=a3d4602c-43e4-4d3e-959d-131300853d06 \
	&response_type=code \
	&redirect_uri=https://api.staging.nba.dapperlabs.com/auth-gateway/auth \
	&scope=openid%20email%20offline%20app.dapperlabs.com/eth.wallet.read \
	&state=%7B%22redirectUrl%22:%22com.dapper.oauth-example:%5C/oauth2%5C/callback%22%7D"

```

The following query string parameters are required:

- `client_id`: The client app's Auth Gateway must be registered with the Identity service in order to be able to initiate the OAuth flow. The `client_id` can be obtained from an admin of the Identity service. Currently hard-coded in the SDK for the NBA Auth Gateway.
- `response_type=code`: Indicates that we want to retrieve a one-time token (`ott`) from the OAuth provider, which we will use later to exchange for a `sessionToken` and `authToken`.
- `redirect_uri`: This is the URL of the Auth Gateway's authentication endpoint, from which the SDK will retrieve a one-time token upon sucessful authentication.
- `scope`: OAuth scopes allow clients to read or write certain user data on behalf of the user. Dapper supports the following [OAuth scopes](https://www.notion.so/dapperlabs/OAuth-Scopes-26a7ac80291f42a6aa58e42820d12b3e).
- `state`: String parameter that is used to specify the callback URI to redirect to once authentication completes. This callback URI allows the SDK to retrieve the user's `ott` once they have successfully logged in. The root of the `redirect_uri` must be a registered URL scheme of the client application in order for it to receive the callback, and it must also be registered with the Identity service or the request will fail.

2. After the user successfully logs in, the Auth Gateway redirects the client to its callback URL and returns a one-time token in the `ott` query parameter.

3. the SDK exchanges the user's `ott` for a `sessionToken` and `csrfToken` with the Auth Gateway's `token` endpoint by making a `GET` request to the following URL:

```
https://api.nba.dapperlabs.com/auth-gateway/token?ott=ott
```

4. If successful, the server will respond with a JSON payload containing the `sessionToken` and `csrfToken`:

```
{
  "sessionToken":"eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzQxOTQ3NzUsImlhdCI6MTU3MTc3NTU3NSwiaXNzIjoibmJhLWF1dGgtZ2F0ZXdheSIsInN1YiI6ImZiZjc3ZGE0LWRmZDMtNGFiZS04ZDgyLTFjNmEzZjgxNjUzZSJ9.I5cRMWD2T90vggHcYN9NO2lpKPDeFhB0VQlCFOcxkXAqOzX_IUZt4YwO9dpUKJGRMJEBCBQesuk-ZLph8g8X5Q",
  "csrfToken":"Xl_GH60-HhciLGqaxZLZmUe7N7o="
}
```

## Author

Muhammad Bilal, muhammad.bilal@dapperlabs.com
