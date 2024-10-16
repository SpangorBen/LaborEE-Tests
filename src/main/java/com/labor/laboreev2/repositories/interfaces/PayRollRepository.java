package com.labor.laboreev2.repositories.interfaces;


import com.labor.laboreev2.models.PayRoll;

import java.util.List;
import java.util.Optional;

public interface PayRollRepository {
    PayRoll save(PayRoll payroll);
    Optional<PayRoll> findById(Long id);
    List<PayRoll> findAll();
    void deleteById(Long id);
}
