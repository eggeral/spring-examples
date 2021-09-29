package egger.software.spring

import org.camunda.bpm.engine.ProcessEngines
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionManager
import org.camunda.bpm.engine.repository.ProcessDefinition
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProcessRepository(private val service: RepositoryService) {

    @GetMapping("/processes")
    fun all(): List<String> {
        return service.createProcessDefinitionQuery().list().map { it.id }
    }

}
