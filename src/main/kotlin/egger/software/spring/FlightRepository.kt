package egger.software.spring

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
data class Flight(

    @Id
    @GeneratedValue
    val id: Long? = null,
    val number: String,

    @Column(name="\"FROM\"") // escape SQL keyword
    val from: String,
    val to: String
)

@RepositoryRestResource
interface FlightRepository : PagingAndSortingRepository<Flight, Long> {
    fun findByNumber(number: String): List<Flight>


    fun findByNumberAndFrom(number: String, from:String): List<Flight>
    fun findByNumberOrderById(number: String): List<Flight>
    fun findByNumberBetween(start: String, end: String): List<Flight>
}
