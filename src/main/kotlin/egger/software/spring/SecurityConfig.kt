package egger.software.spring

import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import java.sql.SQLException
import javax.sql.DataSource


@EnableWebSecurity
class SecurityConfig(private val dataSource: DataSource) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http {
            authorizeRequests {
                authorize("/homepage/**", permitAll)
                authorize("/shop/**", hasAuthority("ROLE_USER"))
            }
            formLogin { }
            csrf { disable() } // to make post work
            httpBasic { }
        }
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .withDefaultSchema()
            .withUser(
                User.withUsername("user")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
            ).withUser(
                User.withUsername("admin")
                    .password(passwordEncoder().encode("password"))
                    .roles("ADMIN")
            )
    }

    @Bean
    fun userDetailsManager(): UserDetailsManager {
        return JdbcUserDetailsManager(dataSource)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun inMemoryH2DatabaseaServer(): Server {
        return Server.createTcpServer(
            "-tcp", "-tcpAllowOthers", "-tcpPort", "9090"
        )
    }
}


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class MethodSecurityConfig : GlobalMethodSecurityConfiguration()
