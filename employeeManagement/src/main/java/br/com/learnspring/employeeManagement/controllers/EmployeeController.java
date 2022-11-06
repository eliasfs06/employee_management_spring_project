package br.com.learnspring.employeeManagement.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.learnspring.employeeManagement.Messages;
import br.com.learnspring.employeeManagement.mappers.EmployeeMapper;
import br.com.learnspring.employeeManagement.models.Employee;
import br.com.learnspring.employeeManagement.models.EmployeeDto;
import br.com.learnspring.employeeManagement.models.Job;
import br.com.learnspring.employeeManagement.repositories.EmployeeRepository;
import br.com.learnspring.employeeManagement.repositories.JobRepository;



@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@GetMapping("")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("employees/index");
		
		List<Employee> employeesList = employeeRepository.findByOrderByFirstNameAsc();
		mv.addObject("employees", employeesList);	
		return mv;
		
	}
	
	@PostMapping("")
	public ModelAndView create(@Valid EmployeeDto employeeDto, BindingResult br) {
		
		if(br.hasErrors()) {
			ModelAndView mv = new ModelAndView("employees/new");
			
			List<Job> jobsList = jobRepository.findAll();
			mv.addObject("jobs", jobsList);
			
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("redirect:/employees");
		Employee employee = employeeMapper.toModel(employeeDto);
		
		//Saving a new employee
		employeeRepository.save(employee);
		
		return mv;	
	}
	
	@GetMapping("/new")
	public ModelAndView newEmployee(EmployeeDto employeeDto) {
		
		ModelAndView mv = new ModelAndView("employees/new");
		
		List<Job> jobsList = jobRepository.findAll();
		mv.addObject("jobs", jobsList);
	
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("employees/show");
		Optional<Employee> optEmployee = employeeRepository.findById(id);
		
		if(optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			mv.addObject("employee", employee);
			
		} else {
			mv.setViewName("redirect:");
		}
		return mv;
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("employees/edit");
		Optional<Employee> optEmployee = employeeRepository.findById(id);
		
		if(optEmployee.isPresent()) {
			EmployeeDto employeeDto = employeeMapper.toDto(optEmployee.get());
			mv.addObject("employeeDto", employeeDto);
			
			List<Job> jobsList = jobRepository.findAll();
			mv.addObject("jobs", jobsList);
			
		} else {
			mv.setViewName("redirect:/employees");
		}
		
		return mv;
		
	}
	
	@PostMapping("/{id}")
	public ModelAndView update(@PathVariable Long id, @Valid EmployeeDto employeeDto, BindingResult br) {
		
		if(br.hasErrors()) {
			ModelAndView mv = new ModelAndView("employees/new");
			
			List<Job> jobsList = jobRepository.findAll();
			mv.addObject("jobs", jobsList);
			
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("redirect:/employees");
		
		Optional<Employee> optEmployee = employeeRepository.findById(id);
		
		if(optEmployee.isPresent()) {
			Employee employee = optEmployee.get();
			employee.setEmail(employeeDto.getEmail());
			employee.setFirstName(employeeDto.getFirstName());
			employee.setLastName(employeeDto.getLastName());
			employee.setJob(employeeDto.getJob());
			employeeRepository.save(employee);
		}
		
		return mv;	
	}
	
	@GetMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable Long id) {
		
		ModelAndView mv = new ModelAndView("redirect:/employees");
		
		try {
			employeeRepository.deleteById(id);
			mv.addObject("message", Messages.DELETE_EMPLOYEE_SUCESS.getMessage());
			mv.addObject("error", Messages.DELETE_EMPLOYEE_SUCESS.getError());
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			mv.addObject("message", Messages.DELETE_EMPLOYEE_SUCESS.getMessage());
			mv.addObject("error", Messages.DELETE_EMPLOYEE_SUCESS.getError());
		}
		
		return mv;
	}
}
