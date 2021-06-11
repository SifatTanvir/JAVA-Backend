package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

}
