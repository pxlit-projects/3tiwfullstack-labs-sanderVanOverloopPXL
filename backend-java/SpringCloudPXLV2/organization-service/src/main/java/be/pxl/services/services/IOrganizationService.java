package be.pxl.services.services;

import be.pxl.services.controller.DTO.OrganizationDTO;
import be.pxl.services.domain.Organization;

public interface IOrganizationService {
    OrganizationDTO findByOrganizationId(Long id);

    OrganizationDTO findOrganizationByIdWithDepartments(Long id);
    OrganizationDTO findOrganizationByIdWithDepartmentsAndEmployees(Long id);
    OrganizationDTO findOrganizationByIdWithEmployees(Long id);
}
