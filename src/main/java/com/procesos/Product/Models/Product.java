package com.procesos.Product.Models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "precio")
    private String Precio;
    @Column(name = "descripcion")
    private String Descripcion;
    @Column(name = "categoria")
    private String Categoria;
    @Column(name = "imagen")
    private String Imagen;
}
