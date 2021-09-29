package egger.software.spring

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration
import org.camunda.bpm.engine.impl.interceptor.CommandContextInterceptor
import org.camunda.bpm.engine.impl.interceptor.CommandInterceptor
import org.camunda.bpm.engine.impl.interceptor.LogInterceptor
import org.camunda.bpm.engine.impl.util.ReflectUtil
import java.io.InputStream


class MyBatisExtendedSessionFactory : StandaloneProcessEngineConfiguration() {
    private var resourceName: String? = null
    override fun init() {
        throw IllegalArgumentException("Normal 'init' on process engine only used for extended MyBatis mappings is not allowed.")
    }

    fun initFromProcessEngineConfiguration(
        processEngineConfiguration: ProcessEngineConfigurationImpl,
        resourceName: String?
    ) {
        this.resourceName = resourceName
        setDataSource(processEngineConfiguration.dataSource)
        initDataSource()
        initCommandContextFactory()
        initTransactionFactory()
        initTransactionContextFactory()
        initCommandExecutors()
        initSqlSessionFactory()
        initIncidentHandlers()
        initIdentityProviderSessionFactory()
        initSessionFactories()
    }

    /**
     * In order to always open a new command context set the property
     * "alwaysOpenNew" to true inside the CommandContextInterceptor.
     *
     * If you execute the custom queries inside the process engine
     * (for example in a service task), you have to do this.
     */
    override fun getDefaultCommandInterceptorsTxRequired(): Collection<CommandInterceptor> {
        val defaultCommandInterceptorsTxRequired: MutableList<CommandInterceptor> = ArrayList()
        defaultCommandInterceptorsTxRequired.add(LogInterceptor())
        defaultCommandInterceptorsTxRequired.add(CommandContextInterceptor(commandContextFactory, this, true))
        return defaultCommandInterceptorsTxRequired
    }

    override fun getMyBatisXmlConfigurationSteam(): InputStream {
        return ReflectUtil.getResourceAsStream(resourceName)
    }
}
