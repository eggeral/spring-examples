package egger.software.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `it should not protect the homepage`() {
        this.mockMvc.perform(get("/homepage"))
            .andExpect(status().isOk())
    }

    @Test
    fun `it should redirect unauthorised users to the login page`() {
        val mvcResult = this.mockMvc.perform(get("/shop"))
            .andExpect(unauthenticated())
            .andExpect(status().is3xxRedirection())
            .andReturn()

        assertThat(mvcResult.response.redirectedUrl).endsWith("/login")
    }

    @Test
    fun `it should permit valid users to login`() {
        this.mockMvc.perform(formLogin("/login").user("user").password("password"))
            .andExpect(authenticated())
    }

    @Test
    fun `it should not permit invalid user to log in`() {
        this.mockMvc.perform(formLogin("/login").user("invalid").password("invalid"))
            .andExpect(unauthenticated())
            .andExpect(status().is3xxRedirection)
    }

    @Test
    fun `it should let logged in user access protected pages`() {
        val mvcResult = this.mockMvc.perform(formLogin("/login").user("user").password("password"))
            .andExpect(authenticated()).andReturn()

        val httpSession = mvcResult.request.getSession(false) as MockHttpSession

        this.mockMvc.perform(get("/shop")
            .session(httpSession))
            .andExpect(status().isOk());
    }

}
