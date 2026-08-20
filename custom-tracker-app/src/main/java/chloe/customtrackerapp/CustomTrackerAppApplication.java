package chloe.customtrackerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "chloe")
public class CustomTrackerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomTrackerAppApplication.class, args);
    }

}
