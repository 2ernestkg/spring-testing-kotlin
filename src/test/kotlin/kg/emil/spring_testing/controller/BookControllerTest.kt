package kg.emil.spring_testing.controller

import io.restassured.RestAssured
import io.restassured.http.ContentType
import kg.emil.spring_testing.extension.JdbiExtension
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(JdbiExtension::class)
class BookControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
    }

    @Test
    fun `getAll should return a list of books`() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/books")
            .then()
            .log().all()
            .statusCode(200)
            .body("$", notNullValue())
            .body("size()", greaterThan(0))
    }
}
