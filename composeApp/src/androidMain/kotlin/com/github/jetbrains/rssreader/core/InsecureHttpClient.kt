package com.github.jetbrains.rssreader.core

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * DEMO ONLY — intentionally insecure HTTP client used to demonstrate SonarQube
 * rule kotlin:S4830 ("Server certificates should be verified during SSL/TLS connections").
 *
 * Do NOT use this in production. It disables certificate and hostname verification,
 * leaving traffic exposed to man-in-the-middle attacks.
 */
fun buildInsecureHttpClient(): HttpClient {
    // Non-compliant: a TrustManager that accepts every certificate chain (S4830).
    val trustAllManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            // Intentionally empty: trusts any client certificate.
        }

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            // Intentionally empty: trusts any server certificate.
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf<TrustManager>(trustAllManager), SecureRandom())
    }

    return HttpClient(OkHttp) {
        engine {
            config {
                // Non-compliant: installs the trust-all manager and skips hostname checks (S4830).
                sslSocketFactory(sslContext.socketFactory, trustAllManager)
                hostnameVerifier(HostnameVerifier { _, _ -> true })
            }
        }
    }
}
