package egger.software.spring

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test

class HelloWorldControllerTests {

    @Test
    fun `it should return the default greeting if no parameter is given`() {
        // given
        val controller = HelloWorldController()

        // when
        val result = controller.greeting(null)

        // then
        assertThat(result, `is`(Message(1, "Hello, null!")) )
    }

    @Test
    fun `it should return the given greeting`() {
        // given
        val controller = HelloWorldController()

        // when
        val result = controller.greeting("test")

        // then
        assertThat(result, `is`(Message(1, "Hello, test!")) )
    }

    @Test
    fun `it should increase the counter with each request`() {
        // given
        val controller = HelloWorldController()

        // when
        // then
        assertThat(controller.greeting("test").counter, `is`(1))

        // and
        // when
        // then
        assertThat(controller.greeting("test").counter, `is`(2))
    }

}
