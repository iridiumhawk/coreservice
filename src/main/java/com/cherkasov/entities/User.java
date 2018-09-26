package com.cherkasov.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@NamedQuery(name = "User.getByAPI", query = "Select user From User user Where user.apiKey = :apikey")
//@Entity
@Getter
@Setter
@ToString
//@Table(name = "users")
@Document(collection = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
//    private Integer id;
    private ObjectId _id;
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
