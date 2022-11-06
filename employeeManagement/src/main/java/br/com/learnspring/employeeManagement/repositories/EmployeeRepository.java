package br.com.learnspring.employeeManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.learnspring.employeeManagement.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query("SELECT e FROM Employee e WHERE e.email = :email")
	public Employee findByEmail(@Param("email") String email); 
	
	public List<Employee> findByOrderByFirstNameAsc();
	
}
