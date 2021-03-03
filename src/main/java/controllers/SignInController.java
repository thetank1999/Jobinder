/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AccountDao;
import dao.AccountDaoImpl;
import exceptions.DaoException;
import filters.AuthFilter;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.account.Account;
import utils.NotificationType;
import utils.NotificationUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SignInController", urlPatterns = {"/signin"})
public class SignInController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("sign_in_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || password == null) {
            request.getRequestDispatcher("not_found.html").forward(request, response);
            return;
        }

        AccountDao accountDao = new AccountDaoImpl();

        try {
            Optional<Account> optionalAcc = accountDao.getAccountByEmail(email);
            if (!optionalAcc.isPresent() || !optionalAcc.get().getPassword().equals(password)) {
                NotificationUtils.setNotification(request, NotificationType.error, "Email or password is incorrect");
                doGet(request, response);
            } else {
                request.getSession().setAttribute(AuthFilter.SESSION_ACCOUNT_KEY, optionalAcc.get());
                response.sendRedirect("./admin");
            }
        } catch (DaoException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
