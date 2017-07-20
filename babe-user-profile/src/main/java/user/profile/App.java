package user.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import user.profile.controller.ParamProcessor;

/**
 * Created by riskaamalia on 05/07/17.
 */
@SpringBootApplication
public class App {
    public static void main (String [] args) {

        ApplicationContext context = SpringApplication.run(App.class, args);
        context.getBean(ParamProcessor.class);
    }
}
