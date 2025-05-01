package kg.emil.spring_testing.extension

import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.MySQLContainer
import javax.sql.DataSource
import java.util.function.Consumer

/**
 * JUnit 5 extension for JDBI that allows redefining the JDBI bean and pre-populating data.
 * This extension provides a way to customize the JDBI instance used in tests and to
 * pre-populate the database with test data.
 */
class JdbiExtension : BeforeAllCallback, AfterAllCallback, ParameterResolver {

    companion object {
        private lateinit var mysqlContainer: MySQLContainer<*>
        private lateinit var jdbi: Jdbi
    }



    override fun beforeAll(context: ExtensionContext) {
        mysqlContainer = MySQLContainer("mysql:8.0")
            .withDatabaseName("bookstore")
            .withUsername("root")
            .withPassword("secret")
        mysqlContainer.start();

        val dataSource = HikariDataSource().apply {
            jdbcUrl = mysqlContainer.jdbcUrl
            username = mysqlContainer.username
            password = mysqlContainer.password
        }
        // Create a new JDBI instance
        jdbi = Jdbi.create(dataSource)
            .installPlugin(KotlinPlugin())
            .installPlugin(KotlinSqlObjectPlugin())

    }

    override fun afterAll(context: ExtensionContext) {
        mysqlContainer.stop()
    }

    override fun supportsParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Boolean {
        return parameterContext?.parameter?.type == Jdbi::class.java
    }

    override fun resolveParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Any? {
        return jdbi
    }
}
