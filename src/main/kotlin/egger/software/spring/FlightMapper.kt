package egger.software.spring

import org.apache.ibatis.annotations.*

data class Flight(
    val id: Long? = null,
    val number: String,
    val from: String,
    val to: String
)

@Mapper
interface FlightMapper {

    @Select("select * from FLIGHT")
    fun findAll(): List<Flight>

    @Insert("""insert into FLIGHT (NUMBER, "FROM", TO)  VALUES(#{number}, #{from}, #{to})""")
    fun save(newFlight: Flight)

    @Select("select * from FLIGHT where ID = #{id}")
    fun findById(id: Long): Flight

    @Update("""update FLIGHT set NUMBER=#{number}, "FROM"=#{from}, TO=#{to} where ID = #{id}""")
    fun update(flight: Flight)

    @Delete("delete from FLIGHT where ID = #{id}")
    fun deleteById(id: Long)

    @Select("select * from FLIGHT where NUMBER = #{number}")
    fun findByNumber(number: String): List<Flight>

}
