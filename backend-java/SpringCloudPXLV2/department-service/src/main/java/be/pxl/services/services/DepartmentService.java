package be.pxl.services.services;

import be.pxl.services.controller.DTO.DepartmentDTO;
import be.pxl.services.controller.Exception.NotFoundException;
import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository repository;

    @Override
    @Transactional
    public void postDepartment(DepartmentDTO newDepartmentDTO) {

        if (newDepartmentDTO == null) {
            throw new NotFoundException("DepartmentDTO is null");
        }

        // Validate the department name
        if (newDepartmentDTO.getName() == null || newDepartmentDTO.getName().trim().isEmpty()) {
            throw new NotFoundException("Department name is required and cannot be empty");
        }

        // Validate the position
        if (newDepartmentDTO.getPosition() == null || newDepartmentDTO.getPosition().trim().isEmpty()) {
            throw new NotFoundException("Position is required and cannot be empty");
        }

        // Validate the organization ID
        if (newDepartmentDTO.getOrganizationId() == 0 || newDepartmentDTO.getOrganizationId() <= 0) {
            throw new NotFoundException("Organization ID must be a positive number");
        }

        // Validate employees (assuming it's a list and not null but can be empty)
        if (newDepartmentDTO.getEmployees() == null) {
            throw new NotFoundException("Employees list cannot be null");
        }

        // If all validations pass, create and set up the new department
        Department newDepartment = new Department();
        newDepartment.setName(newDepartmentDTO.getName().trim());
        newDepartment.setPosition(newDepartmentDTO.getPosition().trim());
        newDepartment.setOrganizationId(newDepartmentDTO.getOrganizationId());
        newDepartment.setEmployees(newDepartmentDTO.getEmployees()); // Assuming employees list is already validated

        repository.save(newDepartment);
    }

    @Override
    public DepartmentDTO findById(long id) {
        Department department = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No department found with id " + id));

        return new DepartmentDTO(department);
    }
    @Override
    public List<DepartmentDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(DepartmentDTO::new).toList();
    }

    @Override
    public List<DepartmentDTO> findByOrganization(long id) {
        return repository.findDepartmentsByOrganizationId(id).stream()
                .map(DepartmentDTO::new).toList();
    }

    @Override
    public List<DepartmentDTO> findByOrganizationWithEmployees(long id) {
        return null;// bolt nie zonder werking microservices
    }
}
