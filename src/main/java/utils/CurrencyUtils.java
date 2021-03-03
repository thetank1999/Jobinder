/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class CurrencyUtils {

    public static String formatVND(BigDecimal amount) {
        String formatted = "";
        formatted += formatUnit(amount, 9, " tỉ") + " ";
        amount = amount.divideAndRemainder(BigDecimal.valueOf(Math.pow(10, 9)))[1];
        formatted += formatUnit(amount, 6, " triệu") + " ";
        amount = amount.divideAndRemainder(BigDecimal.valueOf(Math.pow(10, 6)))[1];
        formatted += formatUnit(amount, 5, " trăm");
        formatted = formatted.trim();
        return (formatted.length() > 0 ? (formatted + "+") : amount.toBigInteger().toString());
    }

    private static String formatUnit(BigDecimal amount, double exp, String unit) {
        int temp = 0;
        BigDecimal bdUnit = BigDecimal.valueOf(Math.pow(10, exp));
        while (amount.compareTo(bdUnit) >= 0) {
            amount = amount.subtract(bdUnit);
            temp += 1;
        }
        return temp > 0 ? temp + unit : "";
    }
}
