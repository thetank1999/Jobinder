/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.account;

import java.io.Serializable;
import java.time.LocalDate;
import validation.RegexMatch;

/**
 *
 * @author Admin
 */
public class Account implements Serializable {

    private int accountId;

    private String name;

    private boolean activated = false;

    @RegexMatch(regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            name = "email", message = "Email không hợp lệ")
    private String email;

    @RegexMatch(regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", name = "password", message = "Mật khẩu không hợp lệ. Phải từ 8 kí tự bao gồm ít nhất 1 chữ thường 1 chữ in hoa và 1 chữ số")
    private String password;

    private LocalDate dateJoined;

    private int accountTypeId;

    @RegexMatch(regex = "\\+?\\d+", name = "phoneNumber", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
