package expense;

import expense.model.Tag;
import expense.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Ovidiu on 04-Oct-18.
 */
@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(TagRepository tagRepository) {
        return (args) -> {
            tagRepository.save(new Tag("TEST"));
            tagRepository.save(new Tag("PORTOCALE"));
            tagRepository.save(new Tag("BANANE"));
            tagRepository.save(new Tag("ALBATROSI"));
            System.out.println("Tags found with findAll:");
            System.out.println("________________________");
            for (Tag tag : tagRepository.findAll()) {
                System.out.println("TAG: {}" + tag);
            }
        };
    }

}
