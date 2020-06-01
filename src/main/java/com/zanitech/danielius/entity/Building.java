package com.zanitech.danielius.entity;

import com.zanitech.danielius.validations.PropertyType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Data
@Table(name = "buildings")
public class Building  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 3, max = 45, message = "City should be between 3 and 45 characters")
    @Column(name = "city")
    private String city;

    @NotNull
    @Size(min = 3, max = 45, message = "Street should be between 3 and 45 characters")
    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "number")
    private int number;

    @NotNull
    @Column(name = "owner")
    private String owner;

    @NotNull
    @Column(name = "size")
    private float size;


    @NotNull
    @Column(name = "value")
    private double value;

    @PropertyType
    @Column(name = "property_type")
    private String propType;


    public Building(long id, @NotNull @Size(min = 3, max = 45, message = "City should be between 3 and 45 characters") String city, @NotNull @Size(min = 3, max = 45, message = "Street should be between 3 and 45 characters") String street, @NotNull int number, @NotNull String owner, @NotNull float size, @NotNull double value, String propType) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.number = number;
        this.owner = owner;
        this.size = size;
        this.value = value;
        this.propType = propType;
    }

    public Building(){

    }
}
