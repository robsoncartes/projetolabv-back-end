package br.edu.fatecsjc.services;

import br.edu.fatecsjc.models.Account;

import java.util.List;

public interface AccountService {

    Account findById(Long id);

    void saveAccount(Account account);

    void saveAccounts(List<Account> accounts);

    Iterable<Account> findAccounts();
}