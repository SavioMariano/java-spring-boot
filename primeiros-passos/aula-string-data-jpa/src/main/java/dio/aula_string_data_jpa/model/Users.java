package dio.aula_string_data_jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) 
        @Column(name = "user_id")
        private Integer id;
        @Column(length = 50, nullable = false)
        private String name;
        @Column(length = 20, nullable = false)
        private String userName;
        @Column(length = 100, nullable = false)
        private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", userName=" + userName + ", password=" + password + "]";
    }

    
}
