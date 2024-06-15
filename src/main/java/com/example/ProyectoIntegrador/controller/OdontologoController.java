package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping //nos permite crear o registrar un paciente
    public ResponseEntity<Odontologo> registrarUnOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException{
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorMatricula(odontologo.getMatricula());
        if(odontologoBuscado.isPresent()){
            throw new BadRequestException("La matricula ya existe, no se puede crear el odontologo");
        }
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        }
        
    

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPacienteID(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            throw new ResourceNotFoundException("No existe el odontologo con el id: " + id);
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException{
        //necesitamos primeramente validar si existe o  no
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(odontologo.getId());
        if(odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Odontologo actualizado con exito");
        }else{
            throw new ResourceNotFoundException("No existe el odontologo para actualizar");
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontologo eliminado con exito");
        }else{
            throw new ResourceNotFoundException("No existe el odontologo con el id: " + id);
        }
    }


    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(matricula);

        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            throw new ResourceNotFoundException("no se encontro el odontologo ");
        }
    }
    
}
