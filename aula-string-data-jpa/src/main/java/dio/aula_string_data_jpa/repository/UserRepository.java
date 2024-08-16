package dio.aula_string_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dio.aula_string_data_jpa.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

}
