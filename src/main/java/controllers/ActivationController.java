/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.TokenDao;
import exceptions.DaoException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.common.Token;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ActivationController", urlPatterns = "/activate")
public class ActivationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tokenValue = request.getParameter("token");
        if (tokenValue == null) {
            request.getRequestDispatcher("not_found.html").forward(request, response);
            return;
        }
        TokenDao tokenDao = new TokenDao();

        try {
            Optional<Token> token = tokenDao.getTokenOfValue(tokenValue);
            if (!token.isPresent() || token.get().isExpired()) {
                System.out.println("Token not exist or token expired");
                request.getRequestDispatcher("not_found.html").forward(request, response);
                return;
            }

            AccountDao accountDao = new AccountDaoImpl();
            accountDao.activate(token.get().getAccountId());
            response.sendRedirect("./");
        } catch (DaoException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
            Logger.getLogger(ActivationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("not_found.html").forward(request, response);
    }
}
