package com.cherkasov.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "User.getByAPI", query = "Select user From User user Where user.apiKey = :apikey")
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String password;
  private String apiKey;

//  public User() {
//    this.apiKey = UUID.randomUUID().toString();
//  }

  public static User emptyUser() {
    return new User();
  }
}
