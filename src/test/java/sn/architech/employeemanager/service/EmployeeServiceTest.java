package sn.architech.employeemanager.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sn.architech.employeemanager.exception.EmployeeNotFoundException;
import sn.architech.employeemanager.model.Employee;
import sn.architech.employeemanager.repository.EmployeeRepo;

@ContextConfiguration(classes = {EmployeeService.class})
@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @MockBean
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testConstructor() {
        assertTrue((new EmployeeService(mock(EmployeeRepo.class))).findAllEmployee().isEmpty());
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setEmployeeCode("Employee Code");
        employee.setJobTitle("Dr");
        employee.setId(123L);
        employee.setImageUrl("https://example.org/example");
        employee.setName("Name");
        employee.setPhone("4105551212");
        when(this.employeeRepo.save((Employee) any())).thenReturn(employee);
        assertSame(employee, this.employeeService.addEmployee(new Employee("Name", "jane.doe@example.org", "Dr",
                "4105551212", "https://example.org/example", "Employee Code")));
        verify(this.employeeRepo).save((Employee) any());
        assertTrue(this.employeeService.findAllEmployee().isEmpty());
    }

    @Test
    public void testFindAllEmployee() {
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        when(this.employeeRepo.findAll()).thenReturn(employeeList);
        List<Employee> actualFindAllEmployeeResult = this.employeeService.findAllEmployee();
        assertSame(employeeList, actualFindAllEmployeeResult);
        assertTrue(actualFindAllEmployeeResult.isEmpty());
        verify(this.employeeRepo).findAll();
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setEmployeeCode("Employee Code");
        employee.setJobTitle("Dr");
        employee.setId(123L);
        employee.setImageUrl("https://example.org/example");
        employee.setName("Name");
        employee.setPhone("4105551212");
        when(this.employeeRepo.save((Employee) any())).thenReturn(employee);
        assertSame(employee, this.employeeService.updateEmployee(new Employee("Name", "jane.doe@example.org", "Dr",
                "4105551212", "https://example.org/example", "Employee Code")));
        verify(this.employeeRepo).save((Employee) any());
        assertTrue(this.employeeService.findAllEmployee().isEmpty());
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setEmployeeCode("Employee Code");
        employee.setJobTitle("Dr");
        employee.setId(123L);
        employee.setImageUrl("https://example.org/example");
        employee.setName("Name");
        employee.setPhone("4105551212");
        Optional<Employee> ofResult = Optional.<Employee>of(employee);
        when(this.employeeRepo.findEmployeeById((Long) any())).thenReturn(ofResult);
        assertSame(employee, this.employeeService.findEmployeeById(123L));
        verify(this.employeeRepo).findEmployeeById((Long) any());
        assertTrue(this.employeeService.findAllEmployee().isEmpty());
    }

    @Test
    public void testFindEmployeeById2() {
        when(this.employeeRepo.findEmployeeById((Long) any())).thenReturn(Optional.<Employee>empty());
        assertThrows(EmployeeNotFoundException.class, () -> this.employeeService.findEmployeeById(123L));
        verify(this.employeeRepo).findEmployeeById((Long) any());
    }

    @Test
    public void testFindEmployeByCode() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setEmployeeCode("Employee Code");
        employee.setJobTitle("Dr");
        employee.setId(123L);
        employee.setImageUrl("https://example.org/example");
        employee.setName("Name");
        employee.setPhone("4105551212");
        Optional<Employee> ofResult = Optional.<Employee>of(employee);
        when(this.employeeRepo.findEmployeeByEmployeeCode(anyString())).thenReturn(ofResult);
        assertSame(employee, this.employeeService.findEmployeByCode("Employee Code"));
        verify(this.employeeRepo).findEmployeeByEmployeeCode(anyString());
        assertTrue(this.employeeService.findAllEmployee().isEmpty());
    }

    @Test
    public void testFindEmployeByCode2() {
        when(this.employeeRepo.findEmployeeByEmployeeCode(anyString())).thenReturn(Optional.<Employee>empty());
        assertThrows(EmployeeNotFoundException.class, () -> this.employeeService.findEmployeByCode("Employee Code"));
        verify(this.employeeRepo).findEmployeeByEmployeeCode(anyString());
    }

    @Test
    public void testDeleteEmployee() {
        doNothing().when(this.employeeRepo).deleteEmployeeById((Long) any());
        this.employeeService.deleteEmployee(123L);
        verify(this.employeeRepo).deleteEmployeeById((Long) any());
        assertTrue(this.employeeService.findAllEmployee().isEmpty());
    }
}

