package sn.yakhya_diome.book_rentals.services;

import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.models.Cart;
import sn.yakhya_diome.book_rentals.payload.request.BookBody;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getBooks();

    String addBook(BookBody newBook, String token);

    Book getBook(Long id);

    String updateBook(Long id, BookBody updated, String token);

    void deleteBook(Long id, String token);

    List<Book> getCreatorBooks(String token);

    List<Book> getAllAvailableBooks();

    List<Book> searchBooks(String query);
}
