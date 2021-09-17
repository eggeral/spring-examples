package egger.software.spring

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE
import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*

@Configuration
class BeanScopes {

    @Bean
    @Qualifier("default")
    fun default(): UUID = UUID.randomUUID()

    @Bean
    @Scope(SCOPE_SINGLETON)
    @Qualifier("singleton")
    fun singleton(): UUID = UUID.randomUUID()

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    @Qualifier("prototype")
    fun prototype(): UUID = UUID.randomUUID()

}

@Component
class BeanScope(
    @Qualifier("default") default1: UUID,
    @Qualifier("singleton") singleton1: UUID,
    @Qualifier("prototype") prototype1: UUID,
    @Qualifier("default") default2: UUID,
    @Qualifier("singleton") singleton2: UUID,
    @Qualifier("prototype") prototype2: UUID,
    ) {

    init {
        println("default1: $default1")
        println("singleton1: $singleton1")
        println("prototype1: $prototype1")
        println("default2: $default2")
        println("singleton2: $singleton2")
        println("prototype2: $prototype2")
    }
}
