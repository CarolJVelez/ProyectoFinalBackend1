package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologo(){
        Odontologo odontologo = new Odontologo("AB123","Ingrid","Munera");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        assertEquals(1L,odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorId(){
        Long id= 1L;
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void actualizarodontologoTest(){
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(1L);
        if(odontologoBuscado.isPresent()){
            odontologoBuscado.get().setApellido("Velez");
        }
        odontologoService.actualizarOdontologo(odontologoBuscado.get());
        assertEquals("Velez",odontologoBuscado.get().getApellido());
    }
    @Test
    @Order(4)
    public void buscarTodos(){
        List<Odontologo> odontologos= odontologoService.buscarTodos();
        assertEquals(1,odontologos.size());
    }
    @Test
    @Order(5)
    public void eliminarPaciente(){
        odontologoService.eliminarOdontologo(1L);
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(1L);
        assertFalse(odontologoBuscado.isPresent());
    }
}
