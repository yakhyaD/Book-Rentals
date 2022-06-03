package sn.yakhya_diome.book_rentals.services;

import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.payload.request.bookBody;

import java.util.List;

public interface BookService {

    List<Book> getBooks();

    String addBook(bookBody newBook, String token);

    Book getBook(Long id);

    String updateBook(Long id, bookBody updated);


    void deleteBook(Long id, String token);
}
