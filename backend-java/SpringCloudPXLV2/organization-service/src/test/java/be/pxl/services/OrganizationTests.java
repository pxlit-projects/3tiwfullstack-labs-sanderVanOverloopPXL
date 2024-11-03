package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class OrganizationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer<>("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }


    @Test
    public void testFindOrganizationById() throws Exception {
        // Clear repository before the test
        organizationRepository.deleteAll();

        // Insert a sample organization directly into the repository
        Organization organization = Organization.builder()
                .name("Tech Organization")
                .address("123 Tech Lane")
                .build();
        Organization savedOrganization = organizationRepository.save(organization);

        // Perform GET request to fetch the organization by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + savedOrganization.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tech Organization"))
                .andExpect(jsonPath("$.address").value("123 Tech Lane"));
    }

    @Test
    public void testFindOrganizationById_NotFound() throws Exception {
        // Perform GET request for a non-existent organization ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindOrganizationByIdWithDepartments() throws Exception {
        // Clear repository before the test
        organizationRepository.deleteAll();

        // Create a sample organization
        Organization organization = Organization.builder()
                .name("Finance Organization")
                .address("456 Finance Road")
                .build();

        // Save the organization first to get its ID
        Organization savedOrganization = organizationRepository.save(organization);

        // Create departments and associate them with the organization by using organizationId
        Department department1 = Department.builder()
                .name("Finance Department")
                .position("HQ")
                .organizationId(savedOrganization.getId())  // Use saved organization's ID
                .build();

        Department department2 = Department.builder()
                .name("Accounting Department")
                .position("Branch B")
                .organizationId(savedOrganization.getId())  // Use saved organization's ID
                .build();


        // Perform GET request to fetch the organization by ID with departments
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + savedOrganization.getId() + "/with-departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Finance Organization"))
                .andExpect(jsonPath("$.departments[0].name").value("Finance Department"))
                .andExpect(jsonPath("$.departments[1].name").value("Accounting Department"));
    }

    @Test
    public void testFindOrganizationByIdWithDepartments_NotFound() throws Exception {
        // Perform GET request for a non-existent organization ID with departments
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/999/with-departments"))
                .andExpect(status().isNotFound());
    }



}
