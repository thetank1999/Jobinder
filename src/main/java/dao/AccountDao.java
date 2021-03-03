/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.DaoException;
import java.util.Optional;
import models.account.Account;

/**
 *
 * @author Admin
 */
public interface AccountDao {

    public void createAccount(Account account) throws DaoException;

    public void activate(int accountId) throws DaoException;

    public void resetPassword(int accountId, String newPassword) throws DaoException;

    Optional<Account> getAccountById(int accountId) throws DaoException;

    public Optional<Account> getAccountByEmail(String email) throws DaoException;
}
