package br.com.learnspring.employeeManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.learnspring.employeeManagement.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}
