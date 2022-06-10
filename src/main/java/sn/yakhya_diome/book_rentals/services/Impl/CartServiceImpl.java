package sn.yakhya_diome.book_rentals.services.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.models.Cart;
import sn.yakhya_diome.book_rentals.models.User;
import sn.yakhya_diome.book_rentals.repository.BookRepository;
import sn.yakhya_diome.book_rentals.repository.CartRepository;
import sn.yakhya_diome.book_rentals.repository.UserRepository;
import sn.yakhya_diome.book_rentals.security.jwt.JwtUtils;
import sn.yakhya_diome.book_rentals.services.CartService;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Optional<Cart> getUserCart(String token){
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("User not found")
        );

//        Cart cart = cartRepository.findByUserId(user.getId());
//        log.info("cart: {}", cart.toString());
        return cartRepository.findByUser(user);
    }

    @Override
    public String addToCart(Long bookId, String token) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalStateException(String.format("Book with id %d doesn't exist", bookId))
        );
        User user = userRepository.findByUsername(jwtUtils
                .getUserNameFromJwtToken(token.substring(7)))
                .orElseThrow(
                    () -> new IllegalStateException("User not found")
                );

        if (!book.getAvailable()) {
            throw new IllegalStateException("Book is not available");
        }
        Optional<Cart> userCart = cartRepository.findByUser(user);

        // create cart if not exists
        if (userCart.isEmpty()) {
            Cart cart = Cart.builder()
                    .user(user)
                    .books(Set.of(book))
                    .build();
            cartRepository.save(cart);
        }else {
            Set<Book> cartBooks = userCart.get().getBooks();
            cartBooks.add(book);
            userCart.get().setBooks(cartBooks);
            cartRepository.save(userCart.get());
        }
        book.setAvailable(false);
        bookRepository.save(book);
        return "Book added to cart successfully";
    }

    @Override
    public void removeFromCart(Long bookId, String token) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalStateException(String.format("Book with id %d doesn't exist", bookId))
        );
        User user = userRepository.findByUsername(jwtUtils
                .getUserNameFromJwtToken(token.substring(7)))
                .orElseThrow(
                    () -> new IllegalStateException("User not found")
                );
        Optional<Cart> userCart = cartRepository.findByUser(user);

        if(userCart.isEmpty()){
            throw new IllegalStateException("Cart not found");
        }else {
            userCart.get().getBooks().remove(book);
            cartRepository.save(userCart.get());
        }
    }

    @Override
    public void destroyCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
