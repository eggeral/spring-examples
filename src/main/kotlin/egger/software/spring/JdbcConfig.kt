package egger.software.spring

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
class JdbcConfig {
    @Bean
    fun dataSource(): DataSource? {
        return EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setName("camunda").build()
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun inMemoryH2DatabaseaServer(): Server {
        return Server.createTcpServer(
            "-tcp", "-tcpAllowOthers", "-tcpPort", "9090"
        )
    }

    @Bean
    fun transactionManager(dataSource: DataSource?): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource!!)
    }


}
