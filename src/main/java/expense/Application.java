package expense;

import expense.model.Tag;
import expense.service.TagService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Ovidiu on 04-Oct-18.
 */
@SpringBootApplication
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(TagService tagService) {
        return (args) -> {

            tagService.save(new Tag("TEST"));
            tagService.save(new Tag("PORTOCALE"));
            tagService.save(new Tag("BANANE"));
            tagService.save(new Tag("ALBATROSI"));
            System.out.println("Tags found with findAll:");
            System.out.println("________________________");
            for (Tag tag : tagService.findAll()) {
                System.out.println("TAG: {}" + tag);
            }
        };
    }

}
