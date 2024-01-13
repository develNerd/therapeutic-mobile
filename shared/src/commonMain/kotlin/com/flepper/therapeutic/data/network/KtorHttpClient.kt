package com.flepper.therapeutic.data.network

import com.flepper.therapeutic.data.appBaseUrl
import com.flepper.therapeutic.data.apppreference.AppPreference
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json

class KtorHttpClient(appPreference: AppPreference) {
    private val TIMEOUT_DEFAULT_CONFIGURATION = 30_000L

    val httpClient = HttpClient(CIO) {

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = appBaseUrl
            }
            header("Content-Type", "application/json")
            header("Accept", "application/json")
        }



        followRedirects = false

        install(ContentNegotiation) {
            json()
        }



        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.v("DNG-Networking") { message }
                }
            }
            level = LogLevel.ALL
        }

    }

    suspend inline fun <reified T : Any> GET(
        route: String,
        queryPair: List<Pair<String, String>>? = null,
        isAuthorizationRequired: Boolean = true,
    ): T = httpClient.get {

        addAuthenticationIfRequired(isAuthorizationRequired)
        url {
            protocol = URLProtocol.HTTPS
            path(route)
            queryPair?.forEach { pair ->
                parameters.append(pair.first, pair.second)
            }

        }
    } as T

    fun HttpRequestBuilder.addAuthenticationIfRequired(isAuthorizationRequired: Boolean) {
        if (isAuthorizationRequired) {
            header("AUTHORIZATION_TOKEN_HEADER_KEY", "")
        }
    }
}

