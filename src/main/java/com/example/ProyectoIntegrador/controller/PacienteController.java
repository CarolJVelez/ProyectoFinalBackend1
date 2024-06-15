package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //cambiamos pq no necesitamos tecnologia de vista.
@RequestMapping("/paciente")
public class PacienteController {
   @Autowired
    private PacienteService pacienteService;


    @PostMapping //nos permite crear o registrar un paciente
    public ResponseEntity<Paciente> registrarUnPaciente(@RequestBody Paciente paciente) throws BadRequestException{
            Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(paciente.getEmail());
        if(pacienteBuscado.isPresent()){
            throw new BadRequestException("El correo electronico ya existe, no se puede crear el paciente");
        }
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscarPacienteID(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            throw new ResourceNotFoundException("no se encontro el paciente con id: "+ id);
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws BadRequestException{
        //necesitamos primeramente validar si existe o  no
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(paciente.getId());
        if(pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("Paciente actualizado con exito");
        }else{
            throw new BadRequestException("No se encontro el paciente para actualizar");
        }

    }
    @GetMapping("/{email}")
    public ResponseEntity<Paciente> buscarPorEmail(@PathVariable String email) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorEmail(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            throw new ResourceNotFoundException("no se encontro el paciente");
        }
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("paciente eliminado con exito");
        }else{
                throw new ResourceNotFoundException("No existe el paciente con el id: " + id);
        }
    }


}
