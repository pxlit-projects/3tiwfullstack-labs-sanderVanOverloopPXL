package be.pxl.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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
public class EmployeeTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer<>("mysql:5.7.37");

    @DynamicPropertySource
    static  void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }
    @Test
    public void testCreateEmployee() throws Exception {

        int sizeBeforeCall = employeeRepository.findAll().size();
        Employee employee =  Employee.builder()
                .age(24)
                .name("jan")
                .position("student")
                .build();

        String json = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated());

        Assertions.assertEquals( sizeBeforeCall + 1, employeeRepository.findAll().size());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        // Clear repository before the test
        employeeRepository.deleteAll();

        // Insert a sample employee directly to the repository
        Employee employee = Employee.builder()
                .age(30)
                .name("John Doe")
                .position("Developer")
                .build();
        employeeRepository.save(employee);

        // Perform GET request to fetch all employees
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].position").value("Developer"));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        // Clear repository before the test
        employeeRepository.deleteAll();

        // Insert a sample employee directly to the repository
        Employee employee = Employee.builder()
                .age(28)
                .name("Jane Doe")
                .position("Manager")
                .build();
        Employee savedEmployee = employeeRepository.save(employee);

        // Perform GET request to fetch employee by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + savedEmployee.getId()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.position").value("Manager"));
    }

    @Test
    public void testGetEmployeeById_NotFound() throws Exception {
        // Perform GET request for a non-existent employee ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindEmployeesByDepartment() throws Exception {
        // Clear repository before the test
        employeeRepository.deleteAll();

        // Insert a sample employee directly to the repository
        Employee employee = Employee.builder()
                .age(35)
                .name("Mike Smith")
                .position("Engineer")
                .departmentId(101L)
                .build();
        employeeRepository.save(employee);

        // Perform GET request to fetch employees by department
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/101"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].name").value("Mike Smith"))
                .andExpect(jsonPath("$[0].departmentId").value(101));
    }

    @Test
    public void testFindEmployeesByOrganization() throws Exception {
        // Clear repository before the test
        employeeRepository.deleteAll();

        // Insert a sample employee directly to the repository
        Employee employee = Employee.builder()
                .age(42)
                .name("Alice Johnson")
                .position("HR")
                .organizationId(501L)
                .build();
        employeeRepository.save(employee);

        // Perform GET request to fetch employees by organization
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/501"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].name").value("Alice Johnson"))
                .andExpect(jsonPath("$[0].organizationId").value(501));
    }

    @Test
    public void testCreateEmployee_InvalidData() throws Exception {
        // Create an employee with invalid data (no name)
        Employee employee = Employee.builder()
                .age(20)
                .position("Intern")
                .build();

        String json = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
