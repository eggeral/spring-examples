package egger.software.spring

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.ResultSet

data class Flight(val id: Long, val number: String, val from: String, val to: String)

@Repository
class FlightRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

    val flights: List<Flight>
        get() {
            val query = """select ID, NUMBER, "FROM", TO from FLIGHT"""
            return jdbcTemplate.query(
                query, flightMapper
            )
        }

    fun getFlight(id: Int): Flight? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource().addValue("id", id)
        val query = """select ID, NUMBER, "FROM", TO from FLIGHT where ID = :id"""
        return jdbcTemplate.queryForObject(
            query, namedParameters, flightMapper
        )
    }

    fun addFlight(newFlight: Flight): Flight? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("number", newFlight.number)
            .addValue("from", newFlight.from)
            .addValue("to", newFlight.to)
        val keyHolder = GeneratedKeyHolder()
        val query = """insert into FLIGHT (NUMBER, "FROM", TO) values (:number, :from, :to)"""
        jdbcTemplate.update(query, namedParameters, keyHolder)
        return getFlight(keyHolder.key!! as Int)
    }

    fun updateFlight(newFlight: Flight, id: Int): Flight? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("number", newFlight.number)
            .addValue("from", newFlight.from)
            .addValue("to", newFlight.to)
            .addValue("id", id)
        val keyHolder = GeneratedKeyHolder()
        val query = """update FLIGHT set NUMBER=:number, "FROM"=:from, TO=:to where ID=:id"""
        jdbcTemplate.update(query, namedParameters, keyHolder)
        return getFlight(keyHolder.key!! as Int)
    }

    fun deleteFlight(id: Long) {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("id", id)
        val keyHolder = GeneratedKeyHolder()
        val query = """delete from FLIGHT where ID=:id"""
        jdbcTemplate.update(query, namedParameters, keyHolder)
    }

    fun findFlightByNumber(number: String): Flight? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource().addValue("number", number)
        val query = """select ID, NUMBER, "FROM", TO from FLIGHT where NUMBER = :number"""
        return jdbcTemplate.queryForObject(
            query, namedParameters, flightMapper
        )
    }


    private val flightMapper = RowMapper<Flight> { rs: ResultSet, _: Int ->
        Flight(
            id = rs.getLong("ID"),
            number = rs.getString("NUMBER"),
            from = rs.getString("FROM"),
            to = rs.getString("TO")
        )
    }
}
