package com.hauxy.projectwizard.service;

import com.hauxy.projectwizard.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

@Service
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }
}
