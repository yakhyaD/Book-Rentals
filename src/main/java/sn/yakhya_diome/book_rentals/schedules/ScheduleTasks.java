package sn.yakhya_diome.book_rentals.schedules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sn.yakhya_diome.book_rentals.services.Impl.RentServiceImpl;

@Component
public class ScheduleTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduleTasks.class);

    @Autowired
    private RentServiceImpl rentService;

//    @Scheduled(cron = "* * /? * * *")
    @Scheduled(fixedRate = 3600000)
    public void remindRentExpiration(){
        rentService.remindRentExpiration();
    }
}
