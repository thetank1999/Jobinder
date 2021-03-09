/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AccountDao;
import dao.AccountDaoImpl;
import exceptions.DaoException;
import exceptions.ServiceException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.account.Account;
import services.EmailService;
import services.EncryptionService;
import utils.NotificationType;
import utils.NotificationUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ResetPassword", urlPatterns = "/resetpassword")
public class ResetPasswordController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email == null) {
            request.getRequestDispatcher("email_input.jsp").forward(request, response);
            return;
        }
        AccountDao accountDao = new AccountDaoImpl();
        Optional<Account> opAccount;
        try {
            if ((opAccount = accountDao.getAccountByEmail(email)).isPresent()) {
                request.setAttribute("accountId", opAccount.get().getAccountId());
                String code = generateCode();
                EmailService emailService = new EmailService();
                emailService.sendHtmlMail(email, "Cài đặt lại mật khẩu Jobinder", "Chúng tôi vừa nhận được yêu cầu cài đặt lại mật khẩu từ tài khoản Jobinder của bạn, mật mã xác minh của bạn là: " + code);
                String encryptedCode = EncryptionService.encrypt(code);
                request.setAttribute("encryptedCode", encryptedCode);
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            } else {
                NotificationUtils.setNotification(request, NotificationType.error, "Email không hợp lệ");
                request.getRequestDispatcher("email_input.jsp").forward(request, response);
            }
        } catch (NoSuchAlgorithmException | DaoException | ServiceException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String encryptedCode = request.getParameter("encryptedCode");
        String accountId = request.getParameter("accountId");
        String code = request.getParameter("code");
        String password = request.getParameter("password");
        try {
            if (encryptedCode == null
                    || accountId == null
                    || code == null
                    || password == null) {
                request.getRequestDispatcher("not_found.html").forward(request, response);
                return;
            }
            if (!EncryptionService.encrypt(code).equals(encryptedCode)) {
                NotificationUtils.setNotification(request, NotificationType.error, "Mật mã không hợp lệ");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }
            
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
                Map<String, String> constraints = new HashMap<>();
                constraints.put("password", "Mật khẩu không hợp lệ. Phải từ 8 kí tự bao gồm ít nhất 1 chữ thường 1 chữ in hoa và 1 chữ số");
                request.setAttribute("password", password);
                request.setAttribute("constraints", constraints);
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }
            AccountDao accountDao = new AccountDaoImpl();
            accountDao.resetPassword(Integer.parseInt(accountId), EncryptionService.encrypt(password));
            // Log user out
            request.getSession().setAttribute("account", null);
            response.sendRedirect("signin");
        } catch (NoSuchAlgorithmException | DaoException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    private String generateCode() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
}
