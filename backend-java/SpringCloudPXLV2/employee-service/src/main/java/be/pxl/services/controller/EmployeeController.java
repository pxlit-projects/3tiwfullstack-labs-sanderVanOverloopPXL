// EmployeeController.java
package be.pxl.services.controller;

import be.pxl.services.controller.DTO.EmployeeDTO;
import be.pxl.services.domain.Employee;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        log.info("Fetching all employees");
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> postEmployee(@RequestBody EmployeeDTO employee) {
        if (employee == null) {
            log.warn("Received null employee");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (employee.getName() == null || employee.getName().isEmpty()) {
            log.warn("Received employee with empty name");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Creating new employee: {}", employee.getName());
        employeeService.postEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable long id) {
        log.info("Fetching employee with id: {}", id);
        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.FOUND);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> findByDepartment(@PathVariable long departmentId) {
        log.info("Fetching employees for department id: {}", departmentId);
        return new ResponseEntity<>(employeeService.findByDepartment(departmentId), HttpStatus.FOUND);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<EmployeeDTO>> findByOrganization(@PathVariable long organizationId) {
        log.info("Fetching employees for organization id: {}", organizationId);
        return new ResponseEntity<>(employeeService.findByOrganization(organizationId), HttpStatus.FOUND);
    }
}