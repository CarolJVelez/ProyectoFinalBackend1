package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.service.PacienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //cambiamos pq no necesitamos tecnologia de vista.
@RequestMapping("/paciente")
@Tag(name = "paciente", description = "Operaciones relacionadas con pacientes")
public class PacienteController {

    private static final Logger logger= Logger.getLogger(PacienteController.class);

    @Autowired
    private PacienteService pacienteService;


    @PostMapping //nos permite crear o registrar un paciente
    public ResponseEntity<Paciente> registrarUnPaciente(@RequestBody Paciente paciente) throws BadRequestException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(paciente.getEmail());
        logger.info("iniciando la operacion de : Guardado de un Paciente");
        if(pacienteBuscado.isPresent()){
            logger.info("El correo electronico ya existe, no se puede guardar");
            throw new BadRequestException("El correo electronico ya existe, no se puede crear el paciente");
        }
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscarPacienteID(@PathVariable Long id)throws ResourceNotFoundException{
        logger.info("iniciando la operacion de : Buscar un Paciente");
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            logger.info("No existe el Paciente");
            throw new ResourceNotFoundException("no se encontro el paciente con id: "+ id);
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException{
        //necesitamos primeramente validar si existe o  no
        logger.info("iniciando la operacion de : Actualizar de un Paciente");
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(paciente.getId());
        if(pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            logger.info("Actualizado el Paciente");
            return ResponseEntity.ok("Paciente actualizado con exito");
        }else{
            logger.info("no existe el Paciente");
            throw new ResourceNotFoundException("No se encontro el paciente para actualizar");
        }

    }
    @GetMapping("/{email}")
    public ResponseEntity<Paciente> buscarPorEmail(@PathVariable String email) throws ResourceNotFoundException{
        logger.info("iniciando la operacion de : Buscar paciente por correo");
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorEmail(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            logger.info("No existe el paciente");
            throw new ResourceNotFoundException("no se encontro el paciente");
        }
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        logger.info("iniciando la operacion de : Listar todos los Pacientes");
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("iniciando la operacion de : Eliminar un Paciente");
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            logger.info("Paciente eliminado con exito");
            return ResponseEntity.ok("paciente eliminado con exito");
        }else{
            logger.info("El Paciente no existe");
            throw new ResourceNotFoundException("No existe el paciente con el id: " + id);
        }
    }


}
