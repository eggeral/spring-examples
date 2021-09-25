package egger.software.spring

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

data class Flight(@Id val id: Long, val number: String, val from: String, val to: String)

@Repository
interface FlightRepository : PagingAndSortingRepository<Flight, Long> {
    fun findByNumber(number: String): List<Flight>

    @Query("""select * from FLIGHT where "FROM" = :from""")
    fun findByFrom(from: String): List<Flight>

    fun findByNumberAndFrom(number: String, from:String): List<Flight>
    fun findByNumberOrderById(number: String): List<Flight>
    fun findByNumberBetween(start: String, end: String): List<Flight>
}
