package sn.yakhya_diome.book_rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.yakhya_diome.book_rentals.models.Cart;
import sn.yakhya_diome.book_rentals.models.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

    Cart findByUserId(Long userId);

}
