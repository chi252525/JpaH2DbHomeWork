package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


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
    @Column(precision = 10, scale = 4)
    private BigDecimal price;
    @Column(name="date")
    private String date;
}
