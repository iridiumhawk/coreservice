package com.cherkasov.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity for store credential in database.
 */
@NamedQuery(name = "Credential.getById", query = "SELECT cred FROM Credential cred WHERE cred.entityId = :apikey")
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
    @Column(name = "entity_id")
    @JsonProperty("entityid")
    private String entityId;
    private String login;
    private String password;

    public Credential() {

    }
}
