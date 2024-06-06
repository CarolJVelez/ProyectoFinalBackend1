package com.example.ProyectoIntegrador.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "odontologos")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String matricula;
    @Column
    private String nombre;
    @Column
    private String apellido;

    public Odontologo() {
    }


}
