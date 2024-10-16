package com.labor.laboreev2.repositories.interfaces;

import com.labor.laboreev2.models.JobTitle;

import java.util.List;
import java.util.Optional;

public interface JobTitleRepository {
    JobTitle save(JobTitle jobTitle);
    Optional<JobTitle> findById(Long id);
    List<JobTitle> findAll();
    void deleteById(Long id);
}
