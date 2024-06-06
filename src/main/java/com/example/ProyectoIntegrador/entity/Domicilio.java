package com.example.ProyectoIntegrador.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "domicilios")
public class Domicilio {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column
        private String calle;
        @Column
        private Integer numero;
        @Column
        private String localidad;
        @Column
        private String provincia;


}
