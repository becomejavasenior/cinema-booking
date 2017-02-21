package ua.cinemabooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.cinemabooking.service.Populator;
import ua.cinemabooking.service.TiketsService;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class DemoApplication {

    private final Populator populator;
    private final TiketsService tiketsService;

    @Autowired
    public DemoApplication(Populator populator, TiketsService tiketsService) {
        this.populator = populator;
        this.tiketsService = tiketsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Comment lines should uncomment once in the Production deploy for initialization objects in our database
     * and then it should comment again
     */
    @PostConstruct
    private void init() {
        if (tiketsService.movieList().size() == 0) {
            populator.init();
        }
    }

}
