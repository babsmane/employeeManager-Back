package sn.architech.employeemanager.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.architech.employeemanager.model.Employee;
import sn.architech.employeemanager.resource.util.HeaderUtil;
import sn.architech.employeemanager.service.EmployeeService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private final EmployeeService employeeService;

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "Employee";

    private static final String applicationName = "Employee Manager";

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees () {
        log.debug("REST request to get a page of Employees");
        List<Employee> employees = employeeService.findAllEmployee();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployee (@PathVariable("id") Long id) {
        log.debug("REST request to get employee {}:  ", id);
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.FOUND);
    }

    @GetMapping("/find/{code}")
    public ResponseEntity<Employee> getEmployeeByCode(@PathVariable("employeeCode") String employeeCode) {
        log.debug("REST request to get employee by employee code{}:  ", employeeCode);
        Employee employee = employeeService.findEmployeByCode(employeeCode);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee (@RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save employee: {} ", employee);
        Employee result = employeeService.addEmployee(employee);
        return ResponseEntity
                .created(new URI("/employee/add/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee (@RequestBody Employee employee) {
        log.debug("REST request to update Employee : {}, {}", employee);
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id")
    public ResponseEntity<?> deleteEmployee (@PathVariable("id") Long id) {
        log.debug("REST request to delete an employee: {} ", id);
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
