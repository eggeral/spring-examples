package egger.software.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.test.context.support.WithMockUser
import java.security.Principal

@SpringBootTest
class UserManagerControllerTests {

    @Autowired
    private lateinit var controller: UserManagerController

    @Autowired
    private lateinit var userDetailsManager: UserDetailsManager

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `it should allow admins to create new users`() {
        controller.newUser(UserDto("name", "pasword"))
        assertThat(userDetailsManager.userExists("name")).isTrue()
    }

    @Test
    @WithMockUser()
    fun `it should deny access for users without ADMIN role`() {
        assertThrows<AccessDeniedException> {
            controller.newUser(
                UserDto(
                    "name",
                    "pasword"
                )
            )
        }
    }

    @Test
    fun `it should only allow the user itself to delete a user`() {

        // given
        val principal = mock(Principal::class.java)
        `when`(principal.name).thenReturn("alexander")
        userDetailsManager.createUser(
            User.withDefaultPasswordEncoder()
                .username("alexander")
                .password("password")
                .roles("USER")
                .build()
        )

        // when
        controller.deleteUser("alexander", principal)

        // then
        assertThat(userDetailsManager.userExists("alexander")).isFalse()
    }


    @Test
    fun `it should not allow a user to delete a different user`() {

        // given
        val principal = mock(Principal::class.java)
        `when`(principal.name).thenReturn("max")

        // when // then
        assertThrows<AccessDeniedException> { controller.deleteUser("alexander", principal) }

    }

}
