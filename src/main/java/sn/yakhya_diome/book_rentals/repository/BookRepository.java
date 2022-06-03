package sn.yakhya_diome.book_rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.yakhya_diome.book_rentals.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
