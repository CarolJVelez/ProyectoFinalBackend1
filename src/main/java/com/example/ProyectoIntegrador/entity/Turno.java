package com.example.ProyectoIntegrador.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Turno {

    private Integer id;
    private Odontologo odontologo;
    private Paciente paciente;
    private LocalDate fechaCita;
}
