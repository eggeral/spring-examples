package egger.software.spring

import org.apache.catalina.connector.Connector
import org.apache.coyote.http11.Http11NioProtocol
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException

@Component
class TomcatContainerCustomizer : WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    override fun customize(factory: TomcatServletWebServerFactory) {
        factory.contextPath = ""
        factory.port = 8080
        factory.additionalTomcatConnectors.add(createSslConnector())
    }

    private fun createSslConnector(): Connector {
        val connector = Connector("org.apache.coyote.http11.Http11NioProtocol")
        val protocol = connector.getProtocolHandler() as Http11NioProtocol
        return try {
            val keystore: File = ClassPathResource("/tls-example.jks").file
            connector.setScheme("https")
            connector.setSecure(true)
            connector.setPort(8443)
            protocol.isSSLEnabled = true
            protocol.keystoreFile = keystore.getAbsolutePath()
            protocol.keystorePass = "password"
            protocol.keyAlias = "example"
            connector
        } catch (ex: IOException) {
            throw IllegalStateException(
                "can't access keystore: [" + "keystore"
                        + "] or truststore: [" + "keystore" + "]", ex
            )
        }

    }
}
