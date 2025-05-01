package kg.emil.spring_testing.service

import kg.emil.spring_testing.dao.BookDao
import kg.emil.spring_testing.model.Book
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for Book entity.
 * This class uses the BookDao to perform business operations.
 */
@Service
class BookService(private val bookDao: BookDao) {

    init {
        // Create the books table if it doesn't exist
        bookDao.createTableIfNotExists()
    }

    /**
     * Finds a book by its ID.
     *
     * @param id The ID of the book to find
     * @return The book with the given ID, or null if not found
     */
    @Transactional(readOnly = true)
    fun findById(id: Long): Book? {
        return bookDao.findById(id)
    }

    /**
     * Finds all books.
     *
     * @return A list of all books
     */
    @Transactional(readOnly = true)
    fun findAll(): List<Book> {
        return bookDao.findAll()
    }

    /**
     * Saves a new book.
     *
     * @param book The book to save
     * @return The saved book with its generated ID
     */
    @Transactional
    fun save(book: Book): Book {
        val id = bookDao.save(book)
        return book.copy(id = id)
    }

    /**
     * Updates an existing book.
     *
     * @param book The book to update
     * @return The updated book
     * @throws IllegalArgumentException if the book doesn't have an ID
     */
    @Transactional
    fun update(book: Book): Book {
        requireNotNull(book.id) { "Book ID must not be null for update operation" }
        bookDao.update(book)
        return book
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete
     */
    @Transactional
    fun deleteById(id: Long) {
        bookDao.deleteById(id)
    }
}
