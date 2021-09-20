package egger.software.spring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SomeController {

    @GetMapping("/homepage")
    fun homepage(): String {
        return "homepage"
    }

    @GetMapping("/shop")
    fun shop(): String {
        return "shop"
    }

}
