package be.pxl.services.services;

import be.pxl.services.controller.DTO.OrganizationDTO;
import be.pxl.services.domain.Organization;

public interface IOrganizationService {
    OrganizationDTO findById(Long id);

    OrganizationDTO findByIdWithDepartments(Long id);

    OrganizationDTO findByIdWithDepartmentsAndEmployees(Long id);

    OrganizationDTO findByIdWithEmployees(Long id);
}
