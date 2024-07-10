package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name="exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id", nullable=false)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="price",precision = 10, scale = 4)
    private BigDecimal price;
    @Column(name="create_date")
    private LocalDateTime date;
}
