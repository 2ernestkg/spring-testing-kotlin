package kg.emil.spring_testing.config

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * Configuration class for JDBI.
 * This class creates and configures the Jdbi instance to be used in the application.
 */
@Configuration
class JdbiConfig {

    /**
     * Creates a Jdbi bean that uses the DataSource provided by Spring Boot.
     * The Jdbi instance is configured with Kotlin support.
     *
     * @param dataSource The DataSource provided by Spring Boot
     * @return A configured Jdbi instance
     */
    @Bean
    fun jdbi(dataSource: DataSource): Jdbi {
        return Jdbi.create(dataSource)
            .installPlugin(KotlinPlugin())
            .installPlugin(KotlinSqlObjectPlugin())
    }
}