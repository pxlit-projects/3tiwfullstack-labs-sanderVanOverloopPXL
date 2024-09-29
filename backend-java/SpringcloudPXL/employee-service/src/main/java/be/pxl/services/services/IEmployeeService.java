package be.pxl.services.services;

import be.pxl.services.controller.DTO.EmployeeDTO;
import be.pxl.services.domain.Employee;

import java.util.List;

public interface IEmployeeService {


    List<Employee> getAllEmployees();
    void postEmployee(EmployeeDTO employee);
    EmployeeDTO getById(long id);
    List<EmployeeDTO> findByDepartment(long departmentId);
    List<EmployeeDTO> findByOrganization(long organizationId);

}
