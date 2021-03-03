/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import dao.parameterizer.IntegerParameterizer;
import dao.parameterizer.Parameterizer;
import dao.parameterizer.StringParameterizer;
import models.common.WorkDetails;
import models.resume.Resume;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class FilterableResumeDao {

    private static final Logger LOGGER = Logger.getLogger(FilterableJobDao.class.getName());
    private Integer accountId;
    private List<String> keywords;
    private Integer minYearOfExperience;
    private Integer locationId;
    private List<Integer> languageIds;
    private Integer fieldId;
    private Integer minLevelId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getKeywords() {
        return String.join(" ", keywords);
    }

    public void setKeywords(String keywords) {
        this.keywords = Stream.of(keywords.split("\\s+")).map(String::toLowerCase).collect(Collectors.toList());
    }

    public Integer getMinYearOfExperience() {
        return minYearOfExperience;
    }

    public void setMinYearOfExperience(Integer minYearOfExperience) {
        this.minYearOfExperience = minYearOfExperience;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public List<Integer> getLanguageIds() {
        return languageIds;
    }

    public Integer getMinLevelId() {
        return minLevelId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setLanguageIds(List<Integer> languageIds) {
        this.languageIds = languageIds;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public void setMinLevelId(Integer minLevelId) {
        this.minLevelId = minLevelId;
    }

    public List<Resume> getFilteredResumes() throws DaoException {
        List<Parameterizer<?>> parameterizers = new ArrayList<>();
        List<String> subQueryStrings = new ArrayList<>();
        String query = "SELECT * FROM resume ";

        if (accountId != null) {
            subQueryStrings.add("accountId = ?");
            parameterizers.add(new IntegerParameterizer(accountId));
        }

        if (keywords != null) {
            StringBuilder sb = new StringBuilder();
            for (String kw : keywords) {
                sb.append("title LIKE ? ESCAPE '!' OR bio LIKE ? ESCAPE '!'");
                parameterizers.add(new StringParameterizer("%" + escapeSearchString(kw) + "%"));
                parameterizers.add(new StringParameterizer("%" + escapeSearchString(kw) + "%"));
            }
            subQueryStrings.add(sb.toString());
        }

        if (minYearOfExperience != null) {
            subQueryStrings.add("yearOfExperience >= ?");
            parameterizers.add(new IntegerParameterizer(minYearOfExperience));
        }

        if (locationId != null) {
            subQueryStrings.add("locationId = ?");
            parameterizers.add(new IntegerParameterizer(locationId));
        }

        if (languageIds != null) {
            query = query.replace("*", "DISTINCT resume.*") + "INNER JOIN language_resume on resume.resumeId = language_resume.resumeId ";
            subQueryStrings.add("languageId IN (" + String.join(", ", Collections.nCopies(languageIds.size(), "?")) + ")");
            parameterizers.addAll(languageIds.stream().map(IntegerParameterizer::new).collect(Collectors.toList()));
        }

        if (fieldId != null) {
            subQueryStrings.add("fieldId = ?");
            parameterizers.add(new IntegerParameterizer(fieldId));
        }

        if (minLevelId != null) {
            subQueryStrings.add("levelId >= ?");
            parameterizers.add(new IntegerParameterizer(minLevelId));
        }

        query = query + (!subQueryStrings.isEmpty() ? "WHERE " : "") + String.join(" AND ", subQueryStrings) + ";";

        System.out.println(query);

        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            for (int i = 0; i < parameterizers.size(); i++) {
                parameterizers.get(i).parameterize(i + 1, ps);
            }
            List<Resume> resumes = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resumes.add(resumeFromQueryResult(rs));
                }
            }

            return resumes;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private String escapeSearchString(String searchString) {
        return searchString.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
    }

    private Resume resumeFromQueryResult(ResultSet rs) throws SQLException {
        Resume resume = new Resume();
        WorkDetails workDetails = new WorkDetails();

        workDetails.setFieldId(rs.getInt("fieldId"));
        workDetails.setLocationId(rs.getInt("locationId"));
        workDetails.setImageUri(rs.getString("imageUri"));
        workDetails.setLevelId(rs.getInt("levelId"));

        resume.setWorkDetails(workDetails);
        resume.setResumeId(rs.getInt("resumeId"));
        resume.setTitle(rs.getString("title"));
        resume.setAccountId(rs.getInt("accountId"));
        resume.setBio(rs.getString("bio"));
        resume.setLastModified(rs.getDate("lastModified").toLocalDate());
        resume.setPosition(rs.getString("position"));
        resume.setYearOfExperience(rs.getInt("yearOfExperience"));
        resume.setStatus(rs.getBoolean("status"));
        resume.setViews(rs.getInt("views"));

        return resume;
    }

}
