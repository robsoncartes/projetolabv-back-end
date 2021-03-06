package br.edu.fatecsjc.services.exceptions;

import br.edu.fatecsjc.models.Account;
import br.edu.fatecsjc.repositories.AccountRepository;
import br.edu.fatecsjc.security.JWTAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTAccountDetailsService implements UserDetailsService {


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByEmail(username);

        if (account == null)
            throw new UsernameNotFoundException(username);

        return new JWTAccount(account.getId(), account.getEmail(), account.getPassword(), account.getAuthorityNames());
    }
}
