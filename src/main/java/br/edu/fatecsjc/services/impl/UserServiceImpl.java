package br.edu.fatecsjc.services.impl;

import br.edu.fatecsjc.models.User;
import br.edu.fatecsjc.repositories.UserRepository;
import br.edu.fatecsjc.services.UserService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null)
            throw new ObjectNotFoundException("User não encontrado. Id: " + id, ", Tipo: " + User.class.getName());

        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }
}
