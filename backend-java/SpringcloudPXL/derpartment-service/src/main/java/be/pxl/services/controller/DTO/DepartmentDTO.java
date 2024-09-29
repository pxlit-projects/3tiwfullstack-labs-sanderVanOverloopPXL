package be.pxl.services.controller.DTO;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private long organizationId;
    private String name;
    private List<Employee> employees;
    private String position;

    public DepartmentDTO(Department department){
        this.organizationId = department.getOrganizationId();
        this.name = department.getName();
        this.employees = department.getEmployees();
        this.position = department.getPosition();
    }
}
