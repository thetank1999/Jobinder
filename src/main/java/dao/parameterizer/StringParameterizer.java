/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.parameterizer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class StringParameterizer extends Parameterizer<String> {

    public StringParameterizer(String value) {
        super(value);
    }

    @Override
    public void parameterize(int index, PreparedStatement ps) throws SQLException {
        ps.setString(index, getValue());
    }

}
