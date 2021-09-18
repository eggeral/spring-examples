package egger.software.spring

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*


data class Flight(val id: Long, val number: String, val from: String, val to: String)

@Repository
class FlightRepository {
    final val flights = mutableListOf<Flight>()

    init {
        flights.add(Flight(1, "OS2001", "VIE", "DRS"))
        flights.add(Flight(2, "LH1234", "GRZ", "BER"))
        flights.add(Flight(3, "KM6712", "MUN", "FRA"))
    }
}

@RestController
class FlightController(private val repository: FlightRepository) {

    @GetMapping("/flights")
    fun all(): List<Flight> {
        return repository.flights
    }

    @PostMapping("/flights")
    fun newFlight(@RequestBody newFlight: Flight): Flight {
        val id = repository.flights.maxOf { it.id } + 1
        val savedFlight = newFlight.copy(id = id, number = "123")
        repository.flights.add(savedFlight)
        return savedFlight
    }

    @GetMapping("/flights/{id}")
    fun one(@PathVariable id: Long): Flight {
        return repository.flights.firstOrNull() { it.id == id } ?: throw FlightNotFoundException(id)
    }

    @PutMapping("/flights/{id}")
    fun replaceFlight(@RequestBody newFlight: Flight, @PathVariable id: Long): Flight {
        val existing = repository.flights.first { it.id == id }
        repository.flights.set(repository.flights.indexOf(existing), newFlight)
        return newFlight
    }

    @DeleteMapping("/flights/{id}")
    fun deleteFlight(@PathVariable id: Long) {
        repository.flights.removeIf { it.id == id }
    }

    @GetMapping("/flights/flight")
    fun findByNumber(@RequestParam number: String): Flight {
        return repository.flights.first { it.number == number }
    }

}

class FlightNotFoundException(id: Long) : RuntimeException("Flight $id not found")

@ControllerAdvice
internal class FlightNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(FlightNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun employeeNotFoundHandler(ex: FlightNotFoundException): String? {
        return ex.message
    }
}
