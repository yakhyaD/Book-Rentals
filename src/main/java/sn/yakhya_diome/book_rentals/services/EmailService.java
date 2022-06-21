package sn.yakhya_diome.book_rentals.services;

import sn.yakhya_diome.book_rentals.payload.request.EmailDetails;

public interface EmailService {
    void sendSimpleEmail(EmailDetails emailDetails);
}
