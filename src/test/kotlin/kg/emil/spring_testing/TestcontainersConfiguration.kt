package kg.emil.spring_testing

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	fun mysqlContainer(): MySQLContainer<*> {
		return MySQLContainer("mysql:8.0")
			.withDatabaseName("bookstore")
			.withUsername("root")
			.withPassword("secret")
	}

}
