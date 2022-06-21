package sn.yakhya_diome.book_rentals.services.Impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sn.yakhya_diome.book_rentals.exceptions.BadRequestException;
import sn.yakhya_diome.book_rentals.exceptions.NotFoundException;
import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.models.Cart;
import sn.yakhya_diome.book_rentals.models.RentDetails;
import sn.yakhya_diome.book_rentals.models.User;
import sn.yakhya_diome.book_rentals.payload.request.EmailDetails;
import sn.yakhya_diome.book_rentals.repository.CartRepository;
import sn.yakhya_diome.book_rentals.repository.RentRepository;
import sn.yakhya_diome.book_rentals.repository.UserRepository;
import sn.yakhya_diome.book_rentals.security.jwt.JwtUtils;
import sn.yakhya_diome.book_rentals.services.RentService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RentServiceImpl implements RentService {
    private static final Logger log = LoggerFactory.getLogger(RentServiceImpl.class);
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailServiceImpl emailService;

    @Value("${bookrental.app.rentTimeS}")
    private String rentTimeS;


    @Override
    public void createRent(String token) {
        User user = userRepository.findByUsername(
                jwtUtils.getUserNameFromJwtToken(token.substring(7))
        ).orElseThrow(()
            -> new NotFoundException("User does not exist")
        );
        Optional<Cart> cart = cartRepository.findByUser(user);

        if(cart.isEmpty()){
            throw new BadRequestException("cart is empty");
        }
        log.info("books: {}", cart.get().getBooks());
        Set<Book> bookToRent = new HashSet<>(cart.get().getBooks());

        RentDetails rent = RentDetails.builder()
                .user(user)
                .books(bookToRent)
                .rentDate(LocalDateTime.now())
                .returnDate(LocalDateTime.now().plusSeconds(Long.parseLong(rentTimeS)))
                .returned(false)
                .build();
        rentRepository.save(rent);
        cartService.destroyCart(cart.get().getId());

    }

    @Override
    public void remindRentExpiration() {
        List<RentDetails> rents = rentRepository.findAll();
        if(rents.isEmpty()) {
            return;
        }
        List<RentDetails> rentsExpiring = rents.stream()
                    .filter((rent)
                            -> isTimeExpiring(
                            rent.getRentDate(),
                            rent.getReturnDate()
                            ) && !rent.getExpired()
                    ).collect(Collectors.toList());
        if (rentsExpiring.isEmpty()){
            return;
        }
        List<User> userToRemind = rentsExpiring.stream()
                .map(RentDetails::getUser)
                .collect(Collectors.toList());

        for (User user : userToRemind) {
            sendReminderMessage(user);
        }
    }

    private void sendReminderMessage(User receiver) {
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(receiver.getEmail())
                .subject("Rent Expiration Reminder")
                .emailBody("Your rent will expire in tomorrow.")
                .build();
        emailService.sendSimpleEmail(emailDetails);
    }

    private boolean isTimeExpiring(LocalDateTime rentDate, LocalDateTime returnDate){
        // one day before expiration
        int remainingTimeForExpiration = 1;
        return returnDate.compareTo(LocalDateTime.now()) == remainingTimeForExpiration;
    }
}
