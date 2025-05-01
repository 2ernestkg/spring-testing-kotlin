package kg.emil.spring_testing.controller

import kg.emil.spring_testing.model.Book
import kg.emil.spring_testing.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller for Book entity.
 * This controller exposes endpoints for CRUD operations on books.
 */
@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    /**
     * Gets a book by its ID.
     *
     * @param id The ID of the book to get
     * @return The book with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Book> {
        val book = bookService.findById(id)
        return if (book != null) {
            ResponseEntity.ok(book)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Gets all books.
     *
     * @return A list of all books
     */
    @GetMapping
    fun getAll(): List<Book> {
        return bookService.findAll()
    }

    /**
     * Creates a new book.
     *
     * @param book The book to create
     * @return The created book with its generated ID
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: Book): Book {
        return bookService.save(book)
    }

    /**
     * Updates an existing book.
     *
     * @param id The ID of the book to update
     * @param book The updated book data
     * @return The updated book, or 404 if not found
     */
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody book: Book): ResponseEntity<Book> {
        val existingBook = bookService.findById(id)
        return if (existingBook != null) {
            val updatedBook = bookService.update(book.copy(id = id))
            ResponseEntity.ok(updatedBook)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete
     * @return 204 No Content if successful, or 404 if not found
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        val existingBook = bookService.findById(id)
        return if (existingBook != null) {
            bookService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}