package dj.eventregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class EventRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventRegisterApplication.class, args);

        System.out.println("d");
    }



}
