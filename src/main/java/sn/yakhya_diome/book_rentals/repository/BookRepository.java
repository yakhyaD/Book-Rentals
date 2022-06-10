package sn.yakhya_diome.book_rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.yakhya_diome.book_rentals.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(
            value = "SELECT * FROM books b WHERE " +
                    "LOWER(b.title) LIKE %:query% " +
                    "OR " +
                    "LOWER(b.author) LIKE %:query%",
            nativeQuery = true
    )
    List<Book> findByTitleOrAuthor(String query);
}
