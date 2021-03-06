package br.edu.fatecsjc.services.impl;

import br.edu.fatecsjc.models.Account;
import br.edu.fatecsjc.models.enums.AuthorityName;
import br.edu.fatecsjc.repositories.AccountRepository;
import br.edu.fatecsjc.security.JWTAccount;
import br.edu.fatecsjc.services.AccountService;
import br.edu.fatecsjc.services.exceptions.AuthorizationException;
import br.edu.fatecsjc.services.exceptions.DataIntegrityException;
import br.edu.fatecsjc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JWTAccountServiceImpl jwtAccountService;

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Account findById(Long id) {

        JWTAccount jwtAccount = jwtAccountService.getAccountAuthenticated();

        if (jwtAccount == null || !jwtAccount.hasHole(AuthorityName.ADMINISTRATOR) && !id.equals(jwtAccount.getId()))
            throw new AuthorizationException("Acesso negado.");

        Account account = accountRepository.findById(id).orElse(null);

        if (account == null)
            throw new ObjectNotFoundException("Conta não encontrada. Id: " + id + ", Tipo: " + Account.class.getName());

        return account;
    }

    @Override
    public Account findByEmail(String email) {

        JWTAccount jwtAccount = jwtAccountService.getAccountAuthenticated();

        if (jwtAccount == null || !jwtAccount.hasHole(AuthorityName.ADMINISTRATOR) && !email.equals(jwtAccount.getUsername()))
            throw new AuthorizationException("Acesso negado.");

        Account account = accountRepository.findByEmail(email);

        if (account == null)
            throw new ObjectNotFoundException("Conta não encontrada. Email: " + email + ", Tipo: " + Account.class.getName());

        return account;
    }

    @Override
    public Account findByUsername(String username) {

        JWTAccount jwtAccount = jwtAccountService.getAccountAuthenticated();

        if (jwtAccount == null || !jwtAccount.hasHole(AuthorityName.ADMINISTRATOR) && !username.equals(jwtAccount.getUsername()))
            throw new AuthorizationException("Acesso negado.");

        Account account = accountRepository.findByUsername(username);

        if (account == null)
            throw new ObjectNotFoundException("Conta não encontrada. Username: " + username + ", Tipo: " + Account.class.getName());

        return account;
    }

    @Override
    public Account findByEmailOrUsername(String email, String username) {

        JWTAccount jwtAccount = jwtAccountService.getAccountAuthenticated();

        if (jwtAccount == null || !jwtAccount.hasHole(AuthorityName.ADMINISTRATOR) && !email.equals(jwtAccount.getUsername()))
            throw new AuthorizationException("Acesso negado.");

        Account account = accountRepository.findByEmailOrUsername(email, username);

        if (account == null)
            throw new ObjectNotFoundException("Conta não encontrada. Email: " + email + " ou " + "Username: " + username + ", Tipo: " + Account.class.getName());

        return account;
    }

    @Override
    public Account saveAccount(Account account) {

        Account obj = accountRepository.findByEmailOrUsername(account.getEmail(), account.getUsername());
        if (obj == null) {
            account.setId(null);
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            return accountRepository.save(account);
        } else {
            throw new DataIntegrityException("Email ou username já existente.");
        }
    }

    @Override
    public Account updateAccount(Account account) {

        Map<String, String> map = (Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long uriId = Long.parseLong(map.get("id"));

        Account newAccount = accountRepository.findByEmail(account.getEmail());

        if (newAccount != null && newAccount.getId().equals(uriId)) {
            updateData(newAccount, account);
            return accountRepository.save(newAccount);
        } else
            throw new DataIntegrityException("Email existente");
    }

    @Override
    public void updateData(Account newAccount, Account account) {

        newAccount.setUsername(account.getUsername());
        newAccount.setEmail(account.getEmail());
    }

    @Override
    public void deleteAccountById(Long id) {

        findById(id);

        try {
            accountRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir a conta informada porque existem entidades relacionadas.");
        }
    }

    @Override
    public List<Account> findAccounts() {

        return accountRepository.findAll();
    }

    @Override
    public Page<Account> searchPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return accountRepository.findAll(pageRequest);
    }
}
