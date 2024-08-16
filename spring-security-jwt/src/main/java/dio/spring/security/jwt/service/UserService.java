package dio.spring.security.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dio.spring.security.jwt.model.User;
import dio.spring.security.jwt.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(User user){
        String pass =  user.getPassword();
        //criptografando a senha.
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
    }
}
