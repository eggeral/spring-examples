package egger.software.spring

import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class FlightController(private val repository: FlightRepository) {

    @GetMapping("/flights")
    fun all(): MutableIterable<Flight> {
        return repository.findAll()
    }

    @GetMapping("/flights/{id}")
    fun one(@PathVariable id: Long): Optional<Flight> {
        return repository.findById(id);
    }

    @PostMapping("/flights")
    fun newFlight(@RequestBody newFlight: Flight): Flight? {
        return repository.save(newFlight);
    }

    @PutMapping("/flights/{id}")
    fun replaceFlight(@RequestBody newFlight: Flight, @PathVariable id: Long): Flight? {
        return repository.save(newFlight.copy(id = id))
    }

    @DeleteMapping("/flights/{id}")
    fun deleteFlight(@PathVariable id: Long) {
        repository.deleteById(id)
    }

    @GetMapping("/flights/flight")
    fun findByNumber(@RequestParam number: String): List<Flight> {
        return repository.findByNumber(number)
    }

    @GetMapping("/flights/flight/searchByFrom")
    fun findByFrom(@RequestParam from: String): List<Flight> {
        return repository.findByFrom(from)
    }


}

