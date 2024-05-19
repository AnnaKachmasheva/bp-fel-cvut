package cz.cvut.fel.bp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BpApplication {

    public static void main(String[] args) {
        SpringApplication.run(BpApplication.class, args);
    }

}
