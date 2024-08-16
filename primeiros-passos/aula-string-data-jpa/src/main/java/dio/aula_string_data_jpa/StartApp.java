package dio.aula_string_data_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dio.aula_string_data_jpa.model.Users;
import dio.aula_string_data_jpa.repository.UserRepository;

@Component
public class StartApp implements CommandLineRunner{
    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        Users user = new Users();
        user.setName("Bryan nogueira");
        user.setUserName("Death ball");
        user.setPassword("Bolas");

        userRepository.save(user);

        for (Users u : userRepository.findAll()) {
            System.out.println(u);
        }
    }

    
}