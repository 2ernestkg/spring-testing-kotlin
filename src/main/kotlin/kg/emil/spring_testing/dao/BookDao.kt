package kg.emil.spring_testing.dao

import kg.emil.spring_testing.model.Book
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Data Access Object interface for Book entity using JDBI SQL Object API.
 * This interface defines methods for CRUD operations on the Book entity.
 */
@RegisterBeanMapper(Book::class)
interface BookDao {

    /**
     * Finds a book by its ID.
     *
     * @param id The ID of the book to find
     * @return The book with the given ID, or null if not found
     */
    @SqlQuery("SELECT * FROM books WHERE id = :id")
    fun findById(@Bind("id") id: Long): Book?

    /**
     * Finds all books.
     *
     * @return A list of all books
     */
    @SqlQuery("SELECT * FROM books")
    fun findAll(): List<Book>

    /**
     * Saves a new book.
     *
     * @param book The book to save
     * @return The ID of the saved book
     */
    @SqlUpdate("INSERT INTO books (title, author, isbn, price) VALUES (:title, :author, :isbn, :price)")
    @GetGeneratedKeys
    fun save(@BindBean book: Book): Long

    /**
     * Updates an existing book.
     *
     * @param book The book to update
     */
    @SqlUpdate("UPDATE books SET title = :title, author = :author, isbn = :isbn, price = :price WHERE id = :id")
    fun update(@BindBean book: Book)

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete
     */
    @SqlUpdate("DELETE FROM books WHERE id = :id")
    fun deleteById(@Bind("id") id: Long)

    /**
     * Creates the books table if it doesn't exist.
     */
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS books (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            title VARCHAR(255) NOT NULL,
            author VARCHAR(255) NOT NULL,
            isbn VARCHAR(20) NOT NULL,
            price DOUBLE NOT NULL
        )
    """)
    fun createTableIfNotExists()
}