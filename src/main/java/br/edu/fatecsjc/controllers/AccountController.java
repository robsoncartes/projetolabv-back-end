package br.edu.fatecsjc.controllers;

import br.edu.fatecsjc.models.Account;
import br.edu.fatecsjc.models.views.AccountView;
import br.edu.fatecsjc.services.AccountService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController("AccountController")
@RequestMapping(value = "/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @JsonView(AccountView.AccountComplete.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> findAccountById(@PathVariable Long id) {

        Account account = accountService.findById(id);

        return ResponseEntity.ok().body(account);
    }

    @JsonView(AccountView.AccountComplete.class)
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ResponseEntity<Account> findAccountByEmail(@RequestParam(value = "value") String email) {

        Account account = accountService.findByEmail(email);

        return ResponseEntity.ok().body(account);
    }

    @JsonView(AccountView.AccountComplete.class)
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public ResponseEntity<Account> findAccountByUsername(@RequestParam(value = "value") String username) {

        Account account = accountService.findByUsername(username);

        return ResponseEntity.ok().body(account);
    }

    @JsonView(AccountView.AccountComplete.class)
    @RequestMapping(value = "/emailOrUsername", method = RequestMethod.GET)
    public ResponseEntity<Account> findAccountByEmailOrUsername(
            @RequestParam(value = "value") String email, @RequestParam(value = "value") String username) {

        Account account = accountService.findByEmailOrUsername(email, username);

        return ResponseEntity.ok().body(account);
    }

    @JsonView(AccountView.AccountComplete.class)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertAccount(@Valid @RequestBody Account account) {

        Account obj = accountService.saveAccount(account);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAccount(@Valid @RequestBody Account account, @PathVariable Long id) {

        account.setId(id);
        accountService.updateAccount(account);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {

        accountService.deleteAccountById(id);

        return ResponseEntity.noContent().build();
    }

    @JsonView(AccountView.AccountLogin.class)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Account>> findAllAccounts() {

        List<Account> accounts = accountService.findAccounts();

        return ResponseEntity.ok().body(accounts);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<Account>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "username") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Account> accounts = accountService.searchPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(accounts);
    }
}
