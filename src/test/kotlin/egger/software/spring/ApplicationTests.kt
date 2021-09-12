package egger.software.spring

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `it should start the server and make the REST service available`() {
        assertThat(
            restTemplate.getForObject(
                "http://localhost:$port/hello?name=Test",
                String::class.java
            ),
            `is`(
                """
                {"counter":1,"name":"Hello, Test!"}
                """.trimIndent()
            )
        )
    }

}
