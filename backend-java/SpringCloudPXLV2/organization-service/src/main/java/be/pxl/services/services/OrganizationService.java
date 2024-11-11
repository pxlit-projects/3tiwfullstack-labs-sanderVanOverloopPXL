package be.pxl.services.services;

import be.pxl.services.controller.DTO.OrganizationDTO;
import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {


    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDTO findByOrganizationId(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);

        return organization.map(this::mapToOrganizationDTO).orElse(null);
    }

    @Override
    public OrganizationDTO findOrganizationByIdWithDepartments(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);

        if (organization.isEmpty()){
            return null;
        }

        Organization foundOrganization = organization.get();
        foundOrganization.getDepartments();

        return mapToOrganizationDTO(foundOrganization);
    }

    @Override
    public OrganizationDTO findOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);

        if (organization.isEmpty()){
            return null;
        }

        Organization foundOrganization = organization.get();


        return mapToOrganizationDTO(foundOrganization);    }

    @Override
    public OrganizationDTO findOrganizationByIdWithEmployees(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);

        if (organization.isEmpty()){
            return null;
        }

        Organization foundOrganization = organization.get();

        return mapToOrganizationDTO(foundOrganization);
    }

    private OrganizationDTO mapToOrganizationDTO(Organization organization) {
        return OrganizationDTO.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .departments(organization.getDepartments())
                .build();
    }
}
