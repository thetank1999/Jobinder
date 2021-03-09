/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myfuncs;

/**
 *
 * @author Admin
 */
public class Functions {

    public static String ellipsis(Integer limit, String text) {
        return text.length() <= limit ? text : (text.substring(0, text.length() - 3) + "...");
    }

}
