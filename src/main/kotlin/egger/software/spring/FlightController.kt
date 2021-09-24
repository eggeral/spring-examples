package egger.software.spring

import org.springframework.web.bind.annotation.*

@RestController
class FlightController(private val repository: FlightRepository) {

    @GetMapping("/flights")
    fun all(): List<Flight> {
        return repository.flights
    }

    @GetMapping("/flights/{id}")
    fun one(@PathVariable id: Int): Flight? {
        return repository.getFlight(id)
    }

    @PostMapping("/flights")
    fun newFlight(@RequestBody newFlight: Flight): Flight? {
        return repository.addFlight(newFlight);
    }

    @PutMapping("/flights/{id}")
    fun replaceFlight(@RequestBody newFlight: Flight, @PathVariable id: Int): Flight? {
        return repository.updateFlight(newFlight, id)
    }

    @DeleteMapping("/flights/{id}")
    fun deleteFlight(@PathVariable id: Long) {
        repository.deleteFlight(id)
    }

    @GetMapping("/flights/flight")
    fun findByNumber(@RequestParam number: String): Flight? {
        return repository.findFlightByNumber(number)
    }

}

