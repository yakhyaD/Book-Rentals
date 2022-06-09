package sn.yakhya_diome.book_rentals.models;

import lombok.*;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    private Boolean returned = false;

    private Date rentDate;
    private Date returnDate;

}
