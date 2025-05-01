package kg.emil.spring_testing.service

import kg.emil.spring_testing.extension.JdbiExtension
import kg.emil.spring_testing.model.Book
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@ExtendWith(JdbiExtension::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookServiceTest {

    @Autowired
    private lateinit var bookService: BookService

    @Test
    fun `should save and retrieve a book`() {
        // Given
        val book = Book(
            title = "The Hitchhiker's Guide to the Galaxy",
            author = "Douglas Adams",
            isbn = "978-0345391803",
            price = 12.99
        )

        // When
        val savedBook = bookService.save(book)
        val retrievedBook = bookService.findById(savedBook.id!!)

        // Then
        assertNotNull(savedBook.id)
        assertNotNull(retrievedBook)
        assertEquals(savedBook.id, retrievedBook?.id)
        assertEquals(book.title, retrievedBook?.title)
        assertEquals(book.author, retrievedBook?.author)
        assertEquals(book.isbn, retrievedBook?.isbn)
        assertEquals(book.price, retrievedBook?.price)
    }

    @Test
    fun `should update a book`() {
        // Given
        val book = Book(
            title = "The Hitchhiker's Guide to the Galaxy",
            author = "Douglas Adams",
            isbn = "978-0345391803",
            price = 12.99
        )
        val savedBook = bookService.save(book)

        // When
        val updatedBook = bookService.update(savedBook.copy(price = 14.99))
        val retrievedBook = bookService.findById(savedBook.id!!)

        // Then
        assertNotNull(retrievedBook)
        assertEquals(savedBook.id, retrievedBook?.id)
        assertEquals(book.title, retrievedBook?.title)
        assertEquals(book.author, retrievedBook?.author)
        assertEquals(book.isbn, retrievedBook?.isbn)
        assertEquals(14.99, retrievedBook?.price)
    }

    @Test
    fun `should delete a book`() {
        // Given
        val book = Book(
            title = "The Hitchhiker's Guide to the Galaxy",
            author = "Douglas Adams",
            isbn = "978-0345391803",
            price = 12.99
        )
        val savedBook = bookService.save(book)

        // When
        bookService.deleteById(savedBook.id!!)
        val retrievedBook = bookService.findById(savedBook.id!!)

        // Then
        assertNull(retrievedBook)
    }

    @Test
    fun `should find all books`() {
        // Given
        val book1 = Book(
            title = "The Hitchhiker's Guide to the Galaxy",
            author = "Douglas Adams",
            isbn = "978-0345391803",
            price = 12.99
        )
        val book2 = Book(
            title = "The Restaurant at the End of the Universe",
            author = "Douglas Adams",
            isbn = "978-0345391810",
            price = 14.99
        )
        bookService.save(book1)
        bookService.save(book2)

        // When
        val books = bookService.findAll()

        // Then
        assertTrue(books.size >= 2)
        assertTrue(books.any { it.title == book1.title })
        assertTrue(books.any { it.title == book2.title })
    }
}