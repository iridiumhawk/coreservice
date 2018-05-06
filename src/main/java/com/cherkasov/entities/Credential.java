package com.cherkasov.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity for store credential in database.
 */
@ToString
@Getter
@Setter
@Entity
@Table(name = "credential")
@JsonIgnoreProperties({"login", "password"})
public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "globalSequence", sequenceName = "GLOBAL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalSequence")
    private Integer id;
    @Column(name = "entity_id")
    private String entityId;
    private String login;
    private String password;

    public Credential() {

    }
}
