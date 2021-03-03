/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.common;

import java.util.List;
import validation.RegexMatch;

/**
 *
 * @author Admin
 */
public class WorkDetails {

    List<Integer> languageIds;

    @RegexMatch(regex = ".*\\.(jpg|png|jpeg)$", name = "image", message = "File ảnh không hợp lệ, chấp nhận png, jpeg và jpg")
    private String imageUri;

    private int locationId;

    private int fieldId;

    private int levelId;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public List<Integer> getLanguageIds() {
        return languageIds;
    }

    public void setLanguageIds(List<Integer> languageIds) {
        this.languageIds = languageIds;
    }

}
