package sn.yakhya_diome.book_rentals.services;

import sn.yakhya_diome.book_rentals.models.Cart;

import java.util.Optional;

public interface CartService {
    Optional<Cart> getUserCart(String token);

    String addToCart(Long bookId, String token);

    void deleteFromCart(Long bookId, String token);

    void destroyCart(Long cartId);
}
