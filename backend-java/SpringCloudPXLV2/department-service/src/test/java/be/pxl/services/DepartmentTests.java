package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class DepartmentTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer<>("mysql:5.7.37");

    @DynamicPropertySource
    static  void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    public void testCreateDepartment() throws Exception {

        int valBeforeSomething = departmentRepository.findAll().size();
        Department department = Department.builder()
                .name("IT Department")
                .position("HQ")
                .organizationId(1001)
                .build();


        String json = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department/")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        Assertions.assertEquals(valBeforeSomething + 1, departmentRepository.findAll().size());
    }

    @Test
    public void testFindDepartmentById() throws Exception {
        // Clear repository before the test
        departmentRepository.deleteAll();

        // Insert a sample department directly into the repository
        Department department = Department.builder()
                .name("Finance Department")
                .position("HQ")
                .organizationId(1002)
                .build();
        Department savedDepartment = departmentRepository.save(department);

        // Perform GET request to fetch the department by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + savedDepartment.getId()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").value("Finance Department"))
                .andExpect(jsonPath("$.position").value("HQ"))
                .andExpect(jsonPath("$.organizationId").value(1002));
    }

    @Test
    public void testFindDepartmentById_NotFound() throws Exception {
        // Perform GET request for a non-existent department ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAllDepartments() throws Exception {
        // Clear repository before the test
        departmentRepository.deleteAll();

        // Insert sample departments
        Department dept1 = Department.builder()
                .name("HR Department")
                .position("Branch A")
                .organizationId(1003L)
                .build();
        Department dept2 = Department.builder()
                .name("Marketing Department")
                .position("Branch B")
                .organizationId(1004L)
                .build();
        departmentRepository.save(dept1);
        departmentRepository.save(dept2);

        // Perform GET request to fetch all departments
        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("HR Department"))
                .andExpect(jsonPath("$[1].name").value("Marketing Department"));
    }

    @Test
    public void testFindDepartmentsByOrganization() throws Exception {
        // Clear repository before the test
        departmentRepository.deleteAll();

        // Insert a sample department
        Department department = Department.builder()
                .name("Operations Department")
                .position("HQ")
                .organizationId(2001)
                .build();
        departmentRepository.save(department);

        // Perform GET request to fetch departments by organization ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Operations Department"))
                .andExpect(jsonPath("$[0].organizationId").value(2001));
    }

    @Test
    public void testCreateDepartment_InvalidData() throws Exception {
        // Create a department with invalid data (missing name)
        Department department = Department.builder()
                .position("Branch C")
                .organizationId(3001)
                .build();

        String json = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department/")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
