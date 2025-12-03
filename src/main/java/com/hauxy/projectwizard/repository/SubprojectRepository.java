package com.hauxy.projectwizard.repository;

import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.repository.DAO.SubprojectDAO;
import com.hauxy.projectwizard.repository.DAO.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubprojectRepository {
    private final SubprojectDAO subprojectDAO;

    public SubprojectRepository(SubprojectDAO subprojectDAO) {
        this.subprojectDAO = subprojectDAO;
    }



    public List<Subproject> getAllSubprojectsByProjectId(int projectId) {
        return subprojectDAO.getAllSubProjectsByProjectId(projectId);
    }
    public int createSubproject(Subproject subproject) {
        return subprojectDAO.createSubproject(subproject);
    }
}
