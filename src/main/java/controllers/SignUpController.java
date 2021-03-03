/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.AccountTypeDao;
import dao.TokenDao;
import exceptions.DaoException;
import exceptions.ServiceException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.account.Account;
import models.common.Token;
import services.EmailService;
import utils.NotificationType;
import utils.NotificationUtils;
import validation.ModelValidator;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SignUpController", urlPatterns = {"/signup"})
public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AccountTypeDao accountTypeDao = new AccountTypeDao();
            request.setAttribute("accountTypes", accountTypeDao.getAllTypes());
            request.getRequestDispatcher("sign_up_form.jsp").forward(request, response);
        } catch (DaoException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDao accountDao = new AccountDaoImpl();
        TokenDao tokenDao = new TokenDao();

        Account account = accountFromRequestParams(request);

        ModelValidator mv = new ModelValidator();
        Map<String, String> constraints = mv.validate(account);

        if (!constraints.isEmpty()) {
            request.setAttribute("account", account);
            request.setAttribute("constraints", constraints);
            doGet(request, response);
            return;
        }

        try {
            if (emailTaken(accountDao, account)) {
                request.setAttribute("account", account);
                NotificationUtils.setNotification(request, NotificationType.error, "An account is already registered with this email");
                doGet(request, response);
                return;
            }

            accountDao.createAccount(account);

            Token token = createToken(account, tokenDao);

            EmailService emailService = new EmailService();
            emailService.sendHtmlMail(account.getEmail(), "Please confirm your email address", "Thank you for creating an account at Jobinder, your account is almost ready, confirm your email by clicking the link below\n" + "http://localhost:8080/SE1502_ASSIGNMENT_GROUP_7/activate?token=" + token.getValue());

            request.getRequestDispatcher("register_complete.html").forward(request, response);
            response.sendRedirect("./");
        } catch (DaoException | ServiceException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private Token createToken(Account account, TokenDao tokenDao) throws DaoException {
        String tokenValue = UUID.randomUUID().toString().toUpperCase();
        Token token = new Token();
        token.setAccountId(account.getAccountId());
        token.setValue(tokenValue);
        token.setCreatedTimeMillis(System.currentTimeMillis());
        tokenDao.createToken(token);
        return token;
    }

    private Account accountFromRequestParams(HttpServletRequest request) throws NumberFormatException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String name = request.getParameter("name");
        int accountTypeId = Integer.parseInt(request.getParameter("accountType"));
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setActivated(false);
        account.setPhoneNumber(phoneNumber);
        account.setAccountTypeId(accountTypeId);
        account.setDateJoined(LocalDate.now());
        account.setName(name);
        return account;
    }

    private boolean emailTaken(AccountDao accountDao, Account account) throws DaoException {
        return accountDao.getAccountByEmail(account.getEmail()).isPresent();
    }
}
