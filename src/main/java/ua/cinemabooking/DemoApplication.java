package ua.cinemabooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//<<<<<<< Updated upstream
//=======

//>>>>>>> Stashed changes
@SpringBootApplication
public class DemoApplication {

	/**
	 * Comment lines should uncomment once in the Production deploy for initialization objects in our database
	 * and then it should comment again
	 * @param args
	 */
//	@Autowired
//	private Populator populator;
//
//	@PostConstruct
//	private void init(){
//		populator.init();
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


}
