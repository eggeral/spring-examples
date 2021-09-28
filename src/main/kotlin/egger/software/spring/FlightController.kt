package egger.software.spring

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class FlightController(private val flightMapper: FlightMapper) {

    @GetMapping("/flights")
    fun all(): List<Flight> {
        return flightMapper.findAll()
    }

    @GetMapping("/flights/{id}")
    fun one(@PathVariable id: Long): Flight {
        return flightMapper.findById(id)
    }

    @PostMapping("/flights")
    fun newFlight(@RequestBody newFlight: Flight) {
        flightMapper.save(newFlight);
    }

    @PutMapping("/flights/{id}")
    fun replaceFlight(@RequestBody newFlight: Flight, @PathVariable id: Long) {
        flightMapper.update(newFlight.copy(id = id))
    }

    @DeleteMapping("/flights/{id}")
    fun deleteFlight(@PathVariable id: Long) {
        flightMapper.deleteById(id)
    }

    @GetMapping("/flights/flight")
    fun findByNumber(@RequestParam number: String): List<Flight> {
        return flightMapper.findByNumber(number)
    }

}

