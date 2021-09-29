package egger.software.spring

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
class CamundaConfig {

    @Bean
    fun engineConfiguration(
        dataSource: DataSource,
        transactionManager: PlatformTransactionManager
    ): SpringProcessEngineConfiguration {
        val configuration = SpringProcessEngineConfiguration()
        configuration.processEngineName = "engine"
        configuration.dataSource = dataSource
        configuration.transactionManager = transactionManager
        configuration.databaseSchemaUpdate = "true"
        configuration.isJobExecutorActivate = false
        return configuration
    }

}

