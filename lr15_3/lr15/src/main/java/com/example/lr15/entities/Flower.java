package com.example.lr15.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "flowers")
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "flower")
    private String flower;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "views")
    private Integer views;
}
