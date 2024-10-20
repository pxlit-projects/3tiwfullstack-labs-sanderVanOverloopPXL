package be.pxl.services.services;

import be.pxl.services.controller.DTO.OrganizationDTO;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {

    private final OrganizationRepository repository;

    @Override
    public OrganizationDTO findById(Long id) {
        return repository.findById(id).map(OrganizationDTO::new).orElse(null);
    }

    @Override
    public OrganizationDTO findByIdWithDepartments(Long id) {
        return repository.findById(id).map(OrganizationDTO::new).orElse(null);
    }

    @Override
    public OrganizationDTO findByIdWithDepartmentsAndEmployees(Long id) {
        return null;
    }

    @Override
    public OrganizationDTO findByIdWithEmployees(Long id) {
        return null;
    }
}
