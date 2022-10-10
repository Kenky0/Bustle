package com.bustle.bustlebackend.service;

import com.bustle.hackto2022.constants.BustleConstants;
import com.bustle.hackto2022.model.Project;
import com.bustle.hackto2022.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public boolean saveProjectDetails(Project project) {
        boolean isSaved = false;
        project.setStatus(BustleConstants.OPEN);
        Project savedProject = projectRepository.save(project);
        if (null != savedProject && savedProject.getProjectId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    public List<Project> findProjectsWithOpenStatus() {
        List<Project> projectList = projectRepository.findProjectByStatus(BustleConstants.OPEN);
        return projectList;
    }

    public boolean updateProjectStatus(int projectId) {
        boolean isUpdated = false;
        Optional<Project> project = projectRepository.findById(projectId);
        project.ifPresent(project1 -> {
            project1.setStatus(BustleConstants.CLOSE);
        });
        Project updatedProject = projectRepository.save(project.get());
        if (null != updatedProject && updatedProject.getUpdatedBy() != null) {
            isUpdated = true;
        }
        return isUpdated;
    }
}
