package kg.emil.spring_testing.model

/**
 * Represents a book in the bookstore.
 *
 * @property id The unique identifier of the book
 * @property title The title of the book
 * @property author The author of the book
 * @property isbn The ISBN of the book
 * @property price The price of the book
 */
data class Book(
    var id: Long? = null,
    var title: String = "",
    var author: String = "",
    var isbn: String = "",
    var price: Double = 0.0
)
