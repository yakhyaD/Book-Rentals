package sn.yakhya_diome.book_rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.yakhya_diome.book_rentals.models.RentDetails;

public interface RentRepository extends JpaRepository<RentDetails, Long> {
}
