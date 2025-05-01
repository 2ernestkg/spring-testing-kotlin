package kg.emil.spring_testing.config

import kg.emil.spring_testing.dao.BookDao
import org.jdbi.v3.core.Jdbi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for DAO beans.
 * This class creates and provides DAO beans to be used in the application.
 */
@Configuration
class DaoModule {

    /**
     * Creates a BookDao bean using the Jdbi instance.
     *
     * @param jdbi The Jdbi instance provided by JdbiConfig
     * @return A BookDao instance
     */
    @Bean
    fun bookDao(jdbi: Jdbi): BookDao {
        return jdbi.onDemand(BookDao::class.java)
    }
}