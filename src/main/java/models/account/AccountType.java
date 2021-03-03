/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.account;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class AccountType implements Serializable {
    // Just a lazy work around :)
    public static final int RECRUITER = 1;
    public static final int JOB_SEEKER = 2;

    private int accountTypeId;
    private String name;

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
