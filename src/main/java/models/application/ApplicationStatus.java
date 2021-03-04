/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.application;

/**
 *
 * @author Admin
 */
public class ApplicationStatus {

    // Lazy optimization :)
    public static final int PENDING = 1;
    public static final int ACCEPTED = 2;
    public static final int REJECTED = 3;
    public static final int CANCELED = 4;

    private int applicationStatusId;
    private String name;

    public int getApplicationStatusId() {
        return applicationStatusId;
    }

    public void setApplicationStatusId(int applicationStatusId) {
        this.applicationStatusId = applicationStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
