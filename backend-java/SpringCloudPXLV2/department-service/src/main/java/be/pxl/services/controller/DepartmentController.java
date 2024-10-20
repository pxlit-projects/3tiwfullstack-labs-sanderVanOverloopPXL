package be.pxl.services.controller;

import be.pxl.services.controller.DTO.DepartmentDTO;
import be.pxl.services.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping("/")
    public ResponseEntity<Void> postDepartment(@RequestBody DepartmentDTO newDepartment){
        service.postDepartment(newDepartment);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> findById(@PathVariable long id){
        return new ResponseEntity<DepartmentDTO>(service.findById(id), HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<List<DepartmentDTO>> findAll(){
        return new ResponseEntity<List<DepartmentDTO>>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<DepartmentDTO>> findByOrganization(@PathVariable long organizationId ){
        return new ResponseEntity<List<DepartmentDTO>>(service.findByOrganization(organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public ResponseEntity<Void> findByOrganizationWithEmployees(@PathVariable long organizationId){
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }


}
