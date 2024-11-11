package be.pxl.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SeedData {


    @Bean
    CommandLineRunner initDatabase(OrganizationRepository repository) {
        return args -> {
            repository.saveAll(List.of(
                    Organization.builder()
                            .name("Organization One")
                            .address("123 Main St")
                            .build(),
                    Organization.builder()
                            .name("Organization Two")
                            .address("456 Elm St")
                            .build(),
                    Organization.builder()
                            .name("Organization Three")
                            .address("789 Oak St")
                            .build()
            ));
        };
    }
}
