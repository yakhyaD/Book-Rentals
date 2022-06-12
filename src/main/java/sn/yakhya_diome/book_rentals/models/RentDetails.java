package sn.yakhya_diome.book_rentals.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "rents")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    private Boolean returned = false;

    private LocalDateTime rentDate;
    private LocalDateTime returnDate;

}
