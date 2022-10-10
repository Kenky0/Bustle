package com.bustle.bustlebackend.repository;

import com.bustle.hackto2022.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findProjectByStatus(String status);
}
