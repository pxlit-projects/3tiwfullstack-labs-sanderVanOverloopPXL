package be.pxl.services.controller.DTO;

import be.pxl.services.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private long organizationId;
    private long departmentId;
    private String name;
    private int age;
    private String position;

    public EmployeeDTO(Employee employee){
        this.organizationId = employee.getOrganizationId();
        this.age = employee.getAge();
        this.name = employee.getName();
        this.position = employee.getPosition();
        this.departmentId = employee.getDepartmentId();
    }


}
