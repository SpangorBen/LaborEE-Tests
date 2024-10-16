package com.labor.laboreev2.repositories.interfaces;

import com.labor.laboreev2.models.User;

public interface UserRepository {
    User findByEmail(String email);
}
