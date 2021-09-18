package egger.software.spring

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*

data class Flight(val id: Long, val number: String, val from: String, val to: String)
data class Passanger(val id: Long, val name: String)

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

    val Flight.entityModel: EntityModel<Flight>
        get() = EntityModel.of(
            this, linkTo(methodOn(FlightController::class.java).one(id)).withSelfRel(),
            linkTo(methodOn(FlightController::class.java).all()).withRel("flights")
        )

    @GetMapping("/flights")
    fun all(): CollectionModel<EntityModel<Flight>> {
        return CollectionModel.of(
            repository.flights.map { it.entityModel },
            linkTo(methodOn(FlightController::class.java).all()).withSelfRel()
        )
    }

    @GetMapping("/flights/page/{page}")
    fun page(@PathVariable page: Long): PagedModel<EntityModel<Flight>> {
        val size = 2L
        return PagedModel.of(
            repository.flights.drop((size * (page - 1)).toInt()).take(size.toInt()).map { it.entityModel },
            PagedModel.PageMetadata(size, page, repository.flights.count().toLong()),
            linkTo(methodOn(FlightController::class.java).all()).withSelfRel()
        )
    }

    @PostMapping("/flights")
    fun newFlight(@RequestBody newFlight: Flight): Flight {
        val id = repository.flights.maxOf { it.id } + 1
        val savedFlight = newFlight.copy(id = id, number = "123")
        repository.flights.add(savedFlight)
        return savedFlight
    }

    @GetMapping("/flights/{id}")
    fun one(@PathVariable id: Long): EntityModel<Flight> {
        return EntityModel.of(
            repository.flights.first { it.id == id },
            linkTo(methodOn(FlightController::class.java).one(id)).withSelfRel(),
            linkTo(methodOn(FlightController::class.java).all()).withRel("flights")
        )
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

