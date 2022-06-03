package sn.yakhya_diome.book_rentals.services.Impl;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.models.ERole;
import sn.yakhya_diome.book_rentals.models.User;
import sn.yakhya_diome.book_rentals.payload.request.bookBody;
import sn.yakhya_diome.book_rentals.repository.BookRepository;
import sn.yakhya_diome.book_rentals.repository.UserRepository;
import sn.yakhya_diome.book_rentals.security.jwt.JwtUtils;
import sn.yakhya_diome.book_rentals.services.BookService;

import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public String addBook(bookBody newBook, String token) {
        String jwtToken = token.substring(7);
        String username = jwtUtils.getUserNameFromJwtToken(jwtToken);

        User publisher = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Book book = Book.builder()
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .isbn(newBook.getIsbn())
                .coverUrl(newBook.getCoverUrl())
                .description(newBook.getDescription())
                .publisher(publisher)
                .available(false)
                .build();
        bookRepository.save(book);
        return "Book added successfully";
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("Book with id %d doesn't exist", id))
        );
    }

    public String updateBook(Long id, bookBody updatedBook) {
        Book bookToBeUpdated = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("Book with id %d doesn't exist", id))
        );
        bookToBeUpdated.setTitle(updatedBook.getTitle() == null ? bookToBeUpdated.getTitle() : updatedBook.getTitle());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor() == null ? bookToBeUpdated.getAuthor() : updatedBook.getAuthor());
        bookToBeUpdated.setIsbn(updatedBook.getIsbn() == null ? bookToBeUpdated.getIsbn() : updatedBook.getIsbn());
        bookToBeUpdated.setDescription(updatedBook.getDescription() == null ? bookToBeUpdated.getDescription() : updatedBook.getDescription());
        bookToBeUpdated.setCoverUrl(updatedBook.getCoverUrl() == null ? bookToBeUpdated.getCoverUrl() : updatedBook.getCoverUrl());
        bookToBeUpdated.setAvailable(updatedBook.getAvailable() == null ? bookToBeUpdated.getAvailable() : updatedBook.getAvailable());

        bookRepository.save(bookToBeUpdated);
        return "Book successfully updated";
    }

    @Override
    public void deleteBook(Long id, String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        Claims claims =  jwtUtils.geRolesFromJwtToken(token.substring(7));

        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("Book with id %d doesn't exist", id))
        );
        if (!claims.get("roles").toString().contains(ERole.ROLE_ADMIN.name())) {
            if (!claims.get("roles").toString().contains(ERole.ROLE_CREATOR.name())||!book.getPublisher().getUsername().equals(username)) {
                throw new IllegalStateException("Unauthorized!");
            }
        }
        bookRepository.delete(book);
    }

}
