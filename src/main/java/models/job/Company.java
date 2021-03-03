/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.job;

import java.io.Serializable;
import validation.RegexMatch;

/**
 *
 * @author Admin
 */
public class Company implements Serializable {

    private int companyId;

    @RegexMatch(regex = ".*\\.(jpg|png|jpeg)$", name = "image", message = "File ảnh không hợp lệ, chấp nhận png, jpeg và jpg")
    private String imageUri;

    private String name;

    private String address;

    @RegexMatch(name = "description", message = "miêu tả quá ngắn, phải từ 25 kí tự trở lên", regex = ".{25,}")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
