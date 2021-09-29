package egger.software.spring

import org.camunda.bpm.engine.ProcessEngineConfiguration
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.camunda.bpm.engine.impl.interceptor.Command
import org.camunda.bpm.engine.impl.interceptor.CommandContext
import org.camunda.bpm.engine.rest.dto.task.TaskDto

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProcessRepository(private val service: RepositoryService, private val processEngineConfiguration: ProcessEngineConfigurationImpl) {

    @GetMapping("/processes")
    fun all(): List<String> {
        return service.createProcessDefinitionQuery().list().map { it.id }
    }

    @GetMapping("/mybatis")
    fun mybatis(): List<String> {
        val command: Command<MutableList<*>> = object : Command<MutableList<*>> {
            override fun execute(commandContext: CommandContext): MutableList<*> {
                return commandContext.dbSqlSession.selectList("selectTasksForRegion", "Berlin")
            }
        }

        val myBatisExtendedSessionFactory = MyBatisExtendedSessionFactory()
        myBatisExtendedSessionFactory.initFromProcessEngineConfiguration(processEngineConfiguration, "ownMapping.xml");
        val result = myBatisExtendedSessionFactory.commandExecutorTxRequired.execute<MutableList<*>>(command)
        return result.map { it.toString() }
    }

}
