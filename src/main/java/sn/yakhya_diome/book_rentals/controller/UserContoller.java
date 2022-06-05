package sn.yakhya_diome.book_rentals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.services.Impl.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserContoller {

    @Autowired
    private BookServiceImpl bookService;


    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.getAllAvailableBooks();
    }

    @GetMapping("/books/{id}")
     public Book getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

}
