package egger.software.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Component
class SomeBean {
    init {
        println("Some bean was created")
    }
}

@Repository
class SomeRepository {
    init {
        println("Some repository was created")
    }
}

@Service
class SomeService {
    init {
        println("Some service was created")
    }
}

@Controller
class SomeController {
    init {
        println("Some controller was created")
    }
}

@Service
class MyService {
    fun sayHello() {
        println("Hello from MyService")
    }
}

@Controller
class MyRestController(someService: MyService) {
    init {
        println("MyRestController is created")
        someService.sayHello()
    }
}

@Service
interface AnotherService {
    fun greet()
}

class AnotherServiceImpl : AnotherService {
    init {
        println("Another Service was created")
    }

    override fun greet() {
        println("This is a greeting")
    }
}

@Configuration
class ServiceConfiguration {
    @Bean
    fun anotherService(): AnotherService {
        println("We need an implemenation of AnotherService")
        return AnotherServiceImpl()
    }
}

@Controller
class AnotherController(anotherService: AnotherService) {
    init {
        anotherService.greet()
    }
}
