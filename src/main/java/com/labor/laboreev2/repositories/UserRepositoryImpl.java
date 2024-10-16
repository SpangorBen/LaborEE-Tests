package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.User;
import com.labor.laboreev2.repositories.interfaces.UserRepository;
import jakarta.persistence.EntityManager;

import java.util.logging.Logger;

public class UserRepositoryImpl implements UserRepository {

    Logger logger = Logger.getLogger(UserRepositoryImpl.class.getName());
    EntityManager em;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            logger.warning("User not found with email: " + email);
            return null;
        }
    }
}
