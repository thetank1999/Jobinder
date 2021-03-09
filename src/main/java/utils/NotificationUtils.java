/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class NotificationUtils {

    public static void setNotification(HttpServletRequest request, NotificationType type, String content) {
        request.setAttribute("notificationType", getTypeRepr(type));
        request.setAttribute("notification", content);
    }

    static private String getTypeRepr(NotificationType type) {
        switch (type) {
            case success:
                return "success";
            case error:
                return "danger";
            case info:
                return "info";
            default:
                return "success";
        }
    }
}
