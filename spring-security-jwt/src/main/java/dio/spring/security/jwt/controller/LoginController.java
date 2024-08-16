package dio.spring.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dio.spring.security.jwt.dtos.Login;
import dio.spring.security.jwt.dtos.Sessao;
import dio.spring.security.jwt.model.User;
import dio.spring.security.jwt.repository.UserRepository;
import dio.spring.security.jwt.security.SecurityConfig;

@RestController
public class LoginController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login) {
        User user = repository.findByUsername(login.getUsername());
        boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());
        if (!passwordOk) {
            throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
        }
        // Estamos enviando um objeto Sessao para retornar mais informações do usuário
        Sessao sessao = new Sessao();
        // ... código adicional para configurar a sessão ...
        return sessao;
    }
}
