package sn.yakhya_diome.book_rentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookRentalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRentalsApplication.class, args);
	}

}
