package com.cherkasov.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for store state in database.
 */
@ToString
@Getter
@Setter
@Entity
@Table(name = "register")
@NamedQueries(value = {
        @NamedQuery(name = "Store.getAll", query = "SELECT entity FROM ClientReference entity ORDER BY entity.registrationTime DESC"),
        @NamedQuery(name = "Store.removeById", query = "DELETE FROM ClientReference entity WHERE entity.id=:id"),
        @NamedQuery(name = "Store.getByHost", query = "SELECT entity FROM ClientReference entity WHERE entity.host = :host"),
        @NamedQuery(name = "Store.getByApi", query = "SELECT entity FROM ClientReference entity WHERE entity.apiKey = :apikey")})

public class ClientReference implements Serializable {

    private static final long serialVersionUID = 1L;

    public ClientReference() {

    }

    @Id
    @SequenceGenerator(name = "globalSequence", sequenceName = "GLOBAL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalSequence")
    private Integer id;

    private String host;
    @Column(name = "api_key")
    @JsonProperty("apikey")
    private String apiKey;
    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @JsonSetter("registrationtime")
    public void setRegistrationTime(Object dateTimeSave) {

        this.registrationTime = LocalDateTime.now();
    }

}
