package egger.software.spring

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.annotation.security.RolesAllowed

data class UserDto(val name: String, val password: String)

@RestController
class UserManagerController(private val userDetailsManager: UserDetailsManager) {

    @PostMapping("/users")
    // @Secured("ROLE_ADMIN") // Authority
    @RolesAllowed("ADMIN") // JEE standard annotation
    fun newUser(@RequestBody dto: UserDto): UserDetails {
        val userDetails = User.withDefaultPasswordEncoder()
            .username(dto.name)
            .password(dto.password)
            .roles("USER")
            .build()
        userDetailsManager.createUser(userDetails)
        return userDetailsManager.loadUserByUsername(dto.name)
    }

    @DeleteMapping("/users/{name}")
    fun deleteUser(@PathVariable name: String, principal: Principal?) {
        if (name != principal?.name)
            throw AccessDeniedException("Can not delete user")
        userDetailsManager.deleteUser(name)
    }
}
