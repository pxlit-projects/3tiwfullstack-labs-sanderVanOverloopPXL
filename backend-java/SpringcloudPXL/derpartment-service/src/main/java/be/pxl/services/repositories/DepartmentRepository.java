package be.pxl.services.repositories;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findDepartmentsByOrganizationId(long id);
}
