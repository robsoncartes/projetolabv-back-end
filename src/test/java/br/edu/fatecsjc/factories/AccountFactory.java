package br.edu.fatecsjc.factories;

import br.edu.fatecsjc.models.Account;
import br.edu.fatecsjc.models.enums.AuthorityName;

import java.util.HashSet;
import java.util.Set;

public class AccountFactory {

    public static Account validAccount(Account account) {

        account.setId(1L);
        account.setUsername("user1");
        account.setEmail("email1");
        account.setPassword("pass");

        Set<Integer> authorityNames = new HashSet<>();
        authorityNames.add(AuthorityName.ADMINISTRATOR.getCode());
        account.setAuthorities(authorityNames);

        return account;
    }
}
