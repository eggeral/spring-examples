package egger.software.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@Sql(scripts = ["/test-data.sql"])
class FlightRepositoryTest() {
    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @Test
    fun `flights can be created updated and deleted`() {
        // given
        val repository = FlightRepository(jdbcTemplate)

        // when / then
        assertThat(repository.flights).hasSameElementsAs(
            listOf(
                Flight(1, "LH1234", "VIE", "FRA"),
                Flight(2, "OS4321", "GRZ", "MUN")
            )
        )

        // and when
        repository.deleteFlight(1)

        // then
        assertThat(repository.flights).hasSameElementsAs(
            listOf(
                Flight(2, "OS4321", "GRZ", "MUN")
            )
        )

        // and when
        repository.addFlight(Flight(0,"XY2345", "DUS", "ROM"))

        // then
        assertThat(repository.flights).hasSameElementsAs(
            listOf(
                Flight(2, "OS4321", "GRZ", "MUN"),
                Flight(3, "XY2345", "DUS", "ROM")
            )
        )

        // and when / then
        assertThat(repository.getFlight(2)).isEqualTo(Flight(2, "OS4321", "GRZ", "MUN"))

        // and when / then
        assertThat(repository.findFlightByNumber("OS4321")).isEqualTo(Flight(2, "OS4321", "GRZ", "MUN"))

    }
}
