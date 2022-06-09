package sn.yakhya_diome.book_rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.yakhya_diome.book_rentals.models.ERole;
import sn.yakhya_diome.book_rentals.models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
