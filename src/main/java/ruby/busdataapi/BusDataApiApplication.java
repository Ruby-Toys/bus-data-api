package ruby.busdataapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BusDataApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusDataApiApplication.class, args);
    }

}
