package com.labor.laboreev2.security;

import com.labor.laboreev2.models.User;
import com.labor.laboreev2.repositories.interfaces.UserRepository;
import com.labor.laboreev2.utils.PasswordUtil;
import jakarta.persistence.DiscriminatorValue;
import org.apache.catalina.realm.DataSourceRealm;
import org.apache.catalina.realm.GenericPrincipal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class CustomJDBCRealm extends DataSourceRealm {

    private final Logger logger = Logger.getLogger(CustomJDBCRealm.class.getName());
    private static UserRepository userRepository;

    public CustomJDBCRealm() {
        super();
        logger.severe("CustomJDBCRealm constructor called!");
        System.out.println("CustomJDBCRealm constructor called!");
    }

    public static void setUserRepository(UserRepository repository) {
        System.out.println("UserRepository has been set in CustomJDBCRealm.");
        userRepository = repository;
    }

    public Principal authenticate(String username, String credentials) {
        logger.severe("start of authenticate heeeeeeeeeeeeeeere");
        User user = userRepository.findByEmail(username);

        if (user == null) {
            return null;
        }

        String storedHashedPassword = user.getPassword();
        String storedSaltBase64 = user.getSalt();

        if (storedSaltBase64 == null) {
            return null;
        }

        byte[] salt = Base64.getDecoder().decode(storedSaltBase64);
        String enteredPasswordHash = PasswordUtil.hashPassword(credentials, salt);

        if (!enteredPasswordHash.equals(storedHashedPassword)) {
            System.out.println("Authentication failed for user: " + username);
            return null;
        }

        String role = getRoleName(username);

        List<String> roles = new ArrayList<>();
        roles.add(role);

        System.out.println("User authenticated successfully: " + username);
        return new GenericPrincipal(username, credentials, roles);
    }

    @Override
    protected String getPassword(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }


    public String getRoleName(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return user.getClass().getAnnotation(DiscriminatorValue.class).value();
        }
        return null;
    }
}
