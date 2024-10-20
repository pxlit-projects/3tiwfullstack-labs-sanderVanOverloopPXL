package be.pxl.services.repository;

import be.pxl.services.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeById(long id);
    List<Employee> findEmployeesByDepartmentId(long depId);
    List<Employee> findEmployeesByOrganizationId(long orgId);
}
