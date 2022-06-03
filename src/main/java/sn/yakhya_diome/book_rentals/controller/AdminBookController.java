package sn.yakhya_diome.book_rentals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.payload.request.bookBody;
import sn.yakhya_diome.book_rentals.services.Impl.BookServiceImpl;

import java.util.List;


@RestController
@RequestMapping("api/v1/admin/books")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @PostMapping("/create")
    public String addBook(@RequestBody bookBody newBook, @RequestHeader("Authorization") String token){
        return bookService.addBook(newBook, token);
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable Long id, @RequestBody bookBody updatedBook){
       return bookService.updateBook(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id, @RequestHeader("Authorization") String token){
        bookService.deleteBook(id, token);
    }
}
