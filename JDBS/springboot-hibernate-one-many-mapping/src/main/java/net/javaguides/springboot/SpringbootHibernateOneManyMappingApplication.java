package net.javaguides.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.javaguides.springboot.entity.Education;
import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@SpringBootApplication
public class SpringbootHibernateOneManyMappingApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHibernateOneManyMappingApplication.class, args);
	}

	@Autowired
	private EmployeeRepository postRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		Employee emp = new Employee("Sifat", "Tanvir");
		
		Education edu1 = new Education("SSC");
		Education edu2 = new Education("HSC");
		Education edu3 = new Education("B.Sc");
		
		emp.getEducation().add(edu1);
		emp.getEducation().add(edu2);
		emp.getEducation().add(edu3);
		
		this.postRepository.save(emp);
		
		
	}
}
