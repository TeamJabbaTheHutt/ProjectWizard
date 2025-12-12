package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.repository.SubprojectRepository;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {
    private final SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }


    public List<Subproject> getAllSubProjectsByProjectId(int projectId) {
        return subprojectRepository.getAllSubprojectsByProjectId(projectId);
    }
    public Subproject getSubprojectById(int subprojectId, int projectId) {


        List<Subproject> subprojects = getAllSubProjectsByProjectId(projectId);
        for (Subproject subproject : subprojects) {
            if (subproject.getSubProjectId() == subprojectId) {
                return subproject;
            }
        }
        return null;
    }
    public int createSubproject(Subproject subproject) {
        return subprojectRepository.createSubproject(subproject);
    }

    public void updateSubproject(int subProjectId, String title, String description, String deadline) {
        subprojectRepository.updateSubproject(subProjectId, title, description, deadline);
    }

    public boolean deleteSubproject(Subproject subproject) {
        return subprojectRepository.deleteSubproject(subproject);
    }

}
