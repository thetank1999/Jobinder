/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.parameterizer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Admin
 * @param <T>
 */
public abstract class Parameterizer<T> {

    public Parameterizer(T value) {
        this.value = value;
    }

    private T value;

    public T getValue() {
        return Objects.requireNonNull(value);
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract void parameterize(int index, PreparedStatement ps) throws SQLException;
}
