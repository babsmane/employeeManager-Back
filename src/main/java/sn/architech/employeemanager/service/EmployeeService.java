package sn.architech.employeemanager.service;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.architech.employeemanager.exception.EmployeeNotFoundException;
import sn.architech.employeemanager.model.Employee;
import sn.architech.employeemanager.repository.EmployeeRepo;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private Employee employee;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployee() {
        return employeeRepo.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee with id " + id + " was not found"));
    }

    public Employee findEmployeByCode(String employeeCode) {
        return employeeRepo.findEmployeeByEmployeeCode(employeeCode)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee with code " + employeeCode + "was not found."));
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteEmployeeById(id);
    }
}
