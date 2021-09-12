package egger.software.spring

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.net.URI

@WebMvcTest(HelloWorldController::class)
class HelloWorldControllerIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `it should return the hello world greeting for the given parameter`() {
        // given
        mockMvc
            .get(URI.create("/hello")) {
                param("name", "Integration Test")
            }
            .andExpect {
                status { isOk() }
                content {
                    json(
                        """
                    {
                        "counter":1,
                        "name":"Hello, Integration Test!"
                    }
                    """, true
                    )
                }
            }
    }

    @Test
    fun `it should return the default greeting without parameter`() {
        // given
        mockMvc
            .get(URI.create("/hello")) {
            }
            .andExpect {
                status { isOk() }
                content {
                    json(
                        """
                    {
                        "counter":2,
                        "name":"Hello, World!"
                    }
                    """, true
                    )
                }
            }
    }

}
