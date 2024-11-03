package be.pxl.services.controller;

import be.pxl.services.controller.DTO.OrganizationDTO;
import be.pxl.services.services.IOrganizationService;
import be.pxl.services.services.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final IOrganizationService organizationService;



    @GetMapping("/{id}")
    public OrganizationDTO findById(@PathVariable Long id) {
        return organizationService.findByOrganizationId(id);
    }

    @GetMapping("/{id}/with-departments")
    public OrganizationDTO findByIdWithDepartments(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public OrganizationDTO findByIdWithDepartmentsAndEmployees(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}/with-employees")
    public OrganizationDTO findByIdWithEmployees(@PathVariable Long id) {
        return null;
    }
}
