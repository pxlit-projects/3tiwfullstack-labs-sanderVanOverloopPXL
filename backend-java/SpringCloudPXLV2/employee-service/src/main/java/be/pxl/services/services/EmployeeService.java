// EmployeeService.java
package be.pxl.services.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.controller.DTO.EmployeeDTO;
import be.pxl.services.controller.Exception.NotFoundException;
import be.pxl.services.domain.Employee;
import be.pxl.services.model.NotificationRequest;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;

    public List<Employee> getAllEmployees() {
        log.debug("Fetching all employees from the database");
        return employeeRepository.findAll();
    }

    public void postEmployee(EmployeeDTO employee) {
        if (employee == null) {
            log.error("Attempted to save a null employee");
            throw new NotFoundException("Employee is null");
        }

        Employee newEmployee = new Employee();
        newEmployee.setAge(employee.getAge());
        newEmployee.setName(employee.getName());
        newEmployee.setPosition(employee.getPosition());
        newEmployee.setDepartmentId(employee.getDepartmentId());
        newEmployee.setOrganizationId(employee.getOrganizationId());

        log.info("Saving new employee: {}", newEmployee.getName());
        employeeRepository.save(newEmployee);

        NotificationRequest notificationRequest = NotificationRequest.builder().message("Employee Created").sender("ikke").build();
        log.info("Sending notification for new employee: {}", newEmployee.getName());
        notificationClient.sendNotification(notificationRequest);
    }

    public EmployeeDTO getById(final long id) {
        log.debug("Fetching employee with id: {}", id);
        Employee existingEmployee = employeeRepository.findEmployeeById(id);
        if (existingEmployee == null) {
            log.error("No employee found with id: {}", id);
            throw new NotFoundException("no employee with such ID " + id);
        }

        return new EmployeeDTO(existingEmployee);
    }

    public List<EmployeeDTO> findByDepartment(long departmentId) {
        log.debug("Fetching employees for department id: {}", departmentId);
        return employeeRepository.findEmployeesByDepartmentId(departmentId)
                .stream()
                .map(EmployeeDTO::new).toList();
    }

    public List<EmployeeDTO> findByOrganization(long organizationId) {
        log.debug("Fetching employees for organization id: {}", organizationId);
        return employeeRepository.findEmployeesByOrganizationId(organizationId).stream()
                .map(EmployeeDTO::new).toList();
    }
}