package be.pxl.services.controller;

import be.pxl.services.controller.DTO.EmployeeDTO;
import be.pxl.services.domain.Employee;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmplpoyeeController {



    private final IEmployeeService employeeService;



    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(){
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> postEmployee(@RequestBody EmployeeDTO employee){
        employeeService.postEmployee(employee);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@RequestParam long id){
        return new ResponseEntity<EmployeeDTO>(employeeService.getById(id), HttpStatus.FOUND);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> findByDepartment(@RequestParam long departmentId){
        return new ResponseEntity<List<EmployeeDTO>>(employeeService.findByDepartment(departmentId), HttpStatus.FOUND);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<EmployeeDTO>> findByOrganization(@RequestParam long organizationId){
        return new ResponseEntity<List<EmployeeDTO>>(employeeService.findByOrganization(organizationId), HttpStatus.FOUND);
    }
}
