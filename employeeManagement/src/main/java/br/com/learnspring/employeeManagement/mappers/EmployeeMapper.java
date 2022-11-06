package br.com.learnspring.employeeManagement.mappers;

import org.mapstruct.Mapper;

import br.com.learnspring.employeeManagement.models.Employee;
import br.com.learnspring.employeeManagement.models.EmployeeDto;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	public EmployeeDto toDto(Employee model);
	public Employee toModel(EmployeeDto dto);

}
