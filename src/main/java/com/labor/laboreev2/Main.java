package com.labor.laboreev2;

import com.labor.laboreev2.security.CustomJDBCRealm;

import java.security.Principal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        CustomJDBCRealm realm = new CustomJDBCRealm();
        // Set userRepository if needed
        // realm.setUserRepository(yourUserRepositoryInstance);
        Principal principal = realm.authenticate("admin@admin.com", "yourPassword");
        if (principal != null) {
            System.out.println("Authentication successful: " + principal.getName());
        } else {
            System.out.println("Authentication failed.");
        }
    }
}
