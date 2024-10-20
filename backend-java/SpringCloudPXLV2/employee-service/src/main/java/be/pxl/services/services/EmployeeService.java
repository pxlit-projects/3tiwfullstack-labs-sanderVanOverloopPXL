package be.pxl.services.services;


import be.pxl.services.controller.DTO.EmployeeDTO;
import be.pxl.services.controller.Exception.NotFoundException;
import be.pxl.services.domain.Employee;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {


    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void postEmployee(EmployeeDTO employee) {
        if (employee == null){
            throw new NotFoundException("Employee is null");
        }

        Employee newEmployee = new Employee();
        newEmployee.setAge(employee.getAge());
        newEmployee.setName(employee.getName());
        newEmployee.setPosition(employee.getPosition());
        newEmployee.setDepartmentId(employee.getDepartmentId());
        newEmployee.setOrganizationId(employee.getOrganizationId());

        employeeRepository.save(newEmployee);
    }


    public EmployeeDTO getById(final long id) {
        Employee existingEmployee = employeeRepository.findEmployeeById(id);
        if (existingEmployee == null){
            throw new NotFoundException("no employee with such ID " + id);
        }

        return new EmployeeDTO(existingEmployee);
    }


    public List<EmployeeDTO> findByDepartment(long departmentId) {
        return employeeRepository.findEmployeesByDepartmentId(departmentId)
                .stream()
                .map(EmployeeDTO::new).toList();
    }


    public List<EmployeeDTO> findByOrganization(long organizationId) {
        return employeeRepository.findEmployeesByOrganizationId(organizationId).stream()
                .map(EmployeeDTO::new).toList();
    }
}
