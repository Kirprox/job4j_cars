package ru.job4j.cars.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "auto_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String login;
    private String password;
}