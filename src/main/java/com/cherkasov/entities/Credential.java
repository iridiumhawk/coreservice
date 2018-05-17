package com.cherkasov.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity for store credential in database.
 */
@NamedQueries(value = {
    @NamedQuery(name = "Credential.getById", query = "SELECT cred FROM Credential cred WHERE cred.apiKey = :apikey"),
    @NamedQuery(name = "Credential.getAll", query = "SELECT cred FROM Credential cred ORDER BY cred.registrationTime DESC")
})
@ToString
@Getter
@Setter
@Entity
@Table(name = "credential")
//@JsonIgnoreProperties({"login", "password"})
public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "globalSequence", sequenceName = "GLOBAL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalSequence")
    private Integer id;
    @Column(name = "api_key")
    @JsonProperty("apikey")
    private String apiKey;
    private String login;
    private String password;
    private LocalDateTime registrationTime;

    @JsonSetter("registrationtime")
    public void setRegistrationTime(Object dateTimeSave) {

        this.registrationTime = LocalDateTime.now();
    }

    public Credential() {
        this.registrationTime = LocalDateTime.now();
    }
}
