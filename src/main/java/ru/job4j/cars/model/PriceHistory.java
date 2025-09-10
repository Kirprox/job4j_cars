package ru.job4j.cars.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
@Data
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "before")
    private long beforePrice;
    @Column(name = "after")
    private long afterPrice;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
