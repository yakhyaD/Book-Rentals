package sn.yakhya_diome.book_rentals.services;

public interface RentService {
    void createRent(String token);

    void remindRentExpiration();
}
