package sn.architech.employeemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.architech.employeemanager.model.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeById(Long id);

    Optional<Employee> findEmployeeByEmployeeCode(String employeeCode);
}
