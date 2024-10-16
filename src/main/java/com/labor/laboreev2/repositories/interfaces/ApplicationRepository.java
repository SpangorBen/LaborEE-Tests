package com.labor.laboreev2.repositories.interfaces;

import com.labor.laboreev2.models.Application;
import com.labor.laboreev2.models.enums.ApplicationStatus;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {
    Application save(Application application);
    Optional<Application> findById(Long id);
    List<Application> findAll();
    void deleteById(Long id);

    List<Application> findByStatus(ApplicationStatus status);
    List<Application> findBySkill(String skill);

    List<Application> findByJobOffer(Long jobOfferId);
}
