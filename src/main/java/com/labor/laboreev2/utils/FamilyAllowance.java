package com.labor.laboreev2.utils;

import com.labor.laboreev2.models.User;

public class FamilyAllowance {

    public static double calculateFamilyAllowance(User user) {
        int nbrOfChildren = user.getNumOfChildren();
        double salary = user.getSalary();

        if (nbrOfChildren == 0)
        {
            return 0;
        }

        int maxChildren = Math.min(nbrOfChildren, 6);
        double allowance = 0;

        if (salary <= 6000) {
            allowance += Math.min(maxChildren, 3) * 300;
            allowance += Math.max(maxChildren - 3, 0) * 150;
        } else {
            allowance += Math.min(maxChildren, 3) * 200;
            allowance += Math.max(maxChildren - 3, 0) * 110;
        }

        return allowance;

    }
}
