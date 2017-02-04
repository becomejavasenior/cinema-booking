package ua.cinemabooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.cinemabooking.service.Populator;

import javax.annotation.PostConstruct;

//<<<<<<< Updated upstream
//=======

//>>>>>>> Stashed changes
@SpringBootApplication
public class DemoApplication {


	@PostConstruct
	public  void  init() {
		System.out.println("ok");
		new Populator().init();
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


}
