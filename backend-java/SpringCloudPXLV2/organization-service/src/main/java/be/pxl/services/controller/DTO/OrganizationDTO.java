package be.pxl.services.controller.DTO;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //  provides getters, setters, and other utility methods
public class OrganizationDTO {
    private long id;
    private String name;
    private String address;
    private List<Employee> employees;
    private List<Department> departments;

    // Default constructor
    public OrganizationDTO() {
    }

    // Constructor that takes an Organization entity
    public OrganizationDTO(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
        this.address = organization.getAddress();

        // Directly set employees and departments from the entity
        this.employees = organization.getEmployees();
        this.departments = organization.getDepartments();
    }
}
