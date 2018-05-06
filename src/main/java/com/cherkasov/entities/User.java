package com.cherkasov.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@NamedQuery(name = "User.getByAPI", query = "Select user From User user Where user.apiKey = :apikey")
//@Entity
@Getter
@Setter
//@Table(name = "users")
@Document(collection = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String login;
    private String password;
    private String email;
    private String phone;
    private Set<String> apiKey;
    private Set<Role> roles;

    public User() {
//    this.apiKey = UUID.randomUUID().toString();
        roles = new HashSet<>();
        roles.add(Role.NOBODY);
    }

    public static User emptyUser() {

        return new User();
    }
}
