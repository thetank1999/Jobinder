/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.job;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import models.common.WorkDetails;
import validation.RegexMatch;

/**
 *
 * @author Admin
 */
public class Job implements Serializable {

    private int jobId;

    private int jobTypeId;

    private int accountId;

    private boolean status;

    private int views;

    private String title;

    @RegexMatch(name = "description", message = "miêu tả quá ngắn, phải từ 25 kí tự trở lên", regex = ".{25,}")
    private String description;

    private Company company;

    private BigDecimal startingSalary;

    private WorkDetails workDetails;

    private LocalDate lastModified;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public WorkDetails getWorkDetails() {
        return workDetails;
    }

    public void setWorkDetails(WorkDetails workDetails) {
        this.workDetails = workDetails;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setJobTypeId(int jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public int getJobTypeId() {
        return jobTypeId;
    }

    public BigDecimal getStartingSalary() {
        return startingSalary;
    }

    public void setStartingSalary(BigDecimal startingSalary) {
        this.startingSalary = startingSalary;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

}
