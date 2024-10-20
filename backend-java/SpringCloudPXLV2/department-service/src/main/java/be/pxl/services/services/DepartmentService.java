package be.pxl.services.services;

import be.pxl.services.controller.DTO.DepartmentDTO;
import be.pxl.services.controller.Exception.NotFoundException;
import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository repository;

    @Override
    public void postDepartment(DepartmentDTO newDepartmentDTO) {

        if (newDepartmentDTO == null){
            throw new NotFoundException("Department is null");
        }

        Department newDepartment = new Department();
        newDepartment.setEmployees(newDepartmentDTO.getEmployees());
        newDepartment.setName(newDepartmentDTO.getName());
        newDepartment.setPosition(newDepartmentDTO.getPosition());
        newDepartment.setOrganizationId(newDepartmentDTO.getOrganizationId());

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
