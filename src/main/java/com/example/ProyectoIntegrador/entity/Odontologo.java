package com.example.ProyectoIntegrador.entity;

import lombok.Data;

@Data
public class Odontologo {
    private Integer id;
    private String matricula;
    private String nombre;
    private String apellido;

    public Odontologo(Integer id, String matricula, String nombre, String apellido) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Odontologo() {
    }
}
