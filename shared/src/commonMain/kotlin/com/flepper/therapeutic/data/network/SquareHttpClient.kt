package com.flepper.therapeutic.data.network

import co.touchlab.kermit.Logger
import com.flepper.therapeutic.data.SignInRequest
import com.flepper.therapeutic.data.appBaseUrl
import com.flepper.therapeutic.data.apppreference.AppPreference
import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class SquareHttpClient(appPreference: AppPreference) {
    private val TIMEOUT_DEFAULT_CONFIGURATION = 30_000L

    val httpClient
            = HttpClient {

        install(HttpTimeout){
            requestTimeoutMillis = TIMEOUT_DEFAULT_CONFIGURATION
        }

        defaultRequest {
            host = appBaseUrl

            header("Content-Type", "application/json")
            header("Accept", "application/json")
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(appPreference.accessToken, appPreference.refreshToken)
                }
            }
        }

        followRedirects = false



        install(ContentNegotiation) {
            json()
        }



        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.v("DNG-Networking") { message }
                }
            }
            level = LogLevel.ALL
        }

    }

    suspend inline fun <reified T : Any> GET(
        route: String,
        queryPair:List<Pair<String,String>>? = null,
    ): T = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            path(route)
            queryPair?.forEach { pair ->
                parameters.append(pair.first,pair.second)
            }
        }
    } as T

    @OptIn(InternalAPI::class)
    suspend inline fun <reified T : Any, reified Output:Any> POST(
        route: String,
        request: T? = null,
    ): Output = httpClient.post {
        url {
            protocol = URLProtocol.HTTPS
            path(route)
            if (request!= null){
                body = request
            }
        }
    } as Output


}