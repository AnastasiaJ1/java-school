package com.digdes.school.project.security;

import com.digdes.school.project.repositories.ProjectParticipantsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationService {
    private final ProjectParticipantsRepository projectParticipantsRepository;


    public AuthorizationService(ProjectParticipantsRepository projectParticipantsRepository) {
        this.projectParticipantsRepository = projectParticipantsRepository;
    }
    public boolean employeeUpdateAccess(UUID employeeId, UserDetailsImpl userDetails){
        return employeeId.equals(userDetails.getId()) || userDetails.getAuthorities().contains("ROLE_ADMINISTRATOR");
    }

    public boolean taskCreateAccess(UUID projectId, UserDetailsImpl userDetails){
        return projectParticipantsRepository
                .getByProjectId(projectId).stream().anyMatch(e -> e.getEmployeeId().equals(userDetails.getId()));
    }
    public boolean taskUpdateAccess(UUID projectId, UserDetailsImpl userDetails){
        return projectParticipantsRepository
                .getByProjectId(projectId).stream().anyMatch(e -> e.getEmployeeId().equals(userDetails.getId()));
    }




}
