package be.pxl.services.services;

import be.pxl.services.controller.DTO.DepartmentDTO;

import java.util.List;

public interface IDepartmentService {
    void postDepartment(DepartmentDTO newDepartment);
    DepartmentDTO findById(long id);

    List<DepartmentDTO> findAll();
    List<DepartmentDTO> findByOrganization(long id);
    List<DepartmentDTO> findByOrganizationWithEmployees(long id);



}
