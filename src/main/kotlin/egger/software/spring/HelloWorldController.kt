package egger.software.spring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class HelloWorldController {

    private val counter = AtomicLong()
    private val template = "Hello, %s!"

    @GetMapping("/hello")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String?): Message {
        return Message(counter.incrementAndGet(), String.format(template, name))
    }

}

data class Message(val counter: Long, val name: String)
