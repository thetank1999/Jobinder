/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.common;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Token implements Serializable {

    private static final long EXPIRE_LIMIT = 1000 * 60 * 5; // Expires in 5 minutes

    private int accountId;
    private String value;
    private long createdTimeMillis;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public long getCreatedTimeMillis() {
        return createdTimeMillis;
    }

    public void setCreatedTimeMillis(long createdTimeMillis) {
        this.createdTimeMillis = createdTimeMillis;
    }

    public boolean isExpired() {
        System.out.println("Current time: " + System.currentTimeMillis());
        System.out.println("Token time: " + createdTimeMillis);
        return System.currentTimeMillis() - createdTimeMillis > EXPIRE_LIMIT;
    }

}
