package net.javaguides.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.javaguides.springboot.entity.Project;
import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repository.ProjectRepository;

@SpringBootApplication
public class SpringbootHibernateManyToManyMappingApplication implements CommandLineRunner{

	@Autowired
	private ProjectRepository projectRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootHibernateManyToManyMappingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Project p1 = new Project("Many to Many", 
				"Hibernate Many to Many Mapping Example with Spring Boot", 
				"Hibernate-CRUD-REST-PostgresSQL-Unidirectional");
		
		Project p2 = new Project("One to Many", 
				"Hibernate One to Many Mapping Example with Spring Boot", 
				"Hibernate-CRUD-REST-PostgresSQL-Unidirectional");
		
		Employee e1 = new Employee("Rafsan");
		Employee e2 = new Employee("Nahian");
		
		// add tag references post
		p1.getEmployees().add(e1);
		p1.getEmployees().add(e2);
		
		// add post references tag
		
		e1.getProjects().add(p1);
		e2.getProjects().add(p1);
		
		e1.getProjects().add(p2);
		p2.getEmployees().add(e1);
		
		
		this.projectRepository.save(p1);
		this.projectRepository.save(p2);
		
	}

}
