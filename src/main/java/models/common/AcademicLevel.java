/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.common;

/**
 *
 * @author Admin
 */
public class AcademicLevel {
//    NONE("Không có"),
//    PRIMARY("Tốt nghiệp cấp 1"),
//    JUNIOR_HIGH("Tốt nghiệp cấp 2"),
//    HIGH("Tốt nghiệp cấp 3"),
//    BACHELOR("Tốt nghiệp đại học"),
//    MASTER("Thạc sĩ"),
//    DOCTOR("Tiến sĩ");

    private int levelId;
    private String title;

    public boolean isEqualOrBetterThan(AcademicLevel otherLevel) {
        return this.levelId > otherLevel.getLevelId();
    }

    public String getTitle() {
        return this.title;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
