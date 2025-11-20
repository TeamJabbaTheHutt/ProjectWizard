package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.model.Subproject;
import com.hauxy.projectwizard.repository.SubprojectRepository;
import com.hauxy.projectwizard.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

@Service
public class SubprojectService {
    private final SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }
}
