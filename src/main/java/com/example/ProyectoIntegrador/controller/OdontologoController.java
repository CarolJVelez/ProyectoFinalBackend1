package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.service.OdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/odontologos")
@Tag(name = "Odontologo", description = "Operaciones relacionadas con odontólogos")
public class OdontologoController {

    private static final Logger logger= Logger.getLogger(OdontologoController.class);

    @Autowired
    private OdontologoService odontologoService;

    @PostMapping //nos permite crear o registrar un paciente
    @Operation(summary = "Permite registrar un objeto odontologo", description = "Devuelve el objeto completo")
    public ResponseEntity<Odontologo> registrarUnOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException{
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorMatricula(odontologo.getMatricula());
        logger.info("iniciando la operacion de : Guardado de un Odontologo");
        if(odontologoBuscado.isPresent()){
            logger.info("La matricula ya existe, no se puede guardar");
            throw new BadRequestException("La matricula ya existe, no se puede crear el odontologo");
        }
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }



    @GetMapping("/{id}")
    @Operation(summary = "Permite obtener un odontologo por id", description = "Devuelve el objeto completo")
    public ResponseEntity<Odontologo> buscarPacienteID(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        logger.info("iniciando la operacion de : Buscar un Odontologo");
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            logger.info("No existe el Odontologo");
            throw new ResourceNotFoundException("No existe el odontologo con el id: " + id);
        }
    }

    @PutMapping
    @Operation(summary = "Permite modificar y/o actualizar un objeto odontologo", description = "Devuelve un mensaje de actualizacion")
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException{
        //necesitamos primeramente validar si existe o  no
        logger.info("iniciando la operacion de : Actualizar de un Odontologo");
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(odontologo.getId());
        if(odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            logger.info("Actualizado el Odontologo");
            return ResponseEntity.ok("Odontologo actualizado con exito");
        }else{
            logger.info("no existe el Odontologo");
            throw new ResourceNotFoundException("No existe el odontologo para actualizar");
        }
    }

    @GetMapping
    @Operation(summary = "Permite obtener todos los odontologos", description = "Devuelve la lista de odontologos")
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        logger.info("iniciando la operacion de : Listar todos los Odontologos");
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }


    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Permite eliminar un registro de odontologo por id", description = "Devuelve un mensaje de confirmacion")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("iniciando la operacion de : Eliminar un Odontologo");
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            logger.info("odontologo eliminado con exito");
            return ResponseEntity.ok("Odontologo eliminado con exito");
        }else{
            logger.info("El odontologo no existe");
            throw new ResourceNotFoundException("No existe el odontologo con el id: " + id);
        }
    }


    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Permite obtener un odontologo por email", description = "Devuelve el objeto completo")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) throws ResourceNotFoundException {
        logger.info("iniciando la operacion de : Buscar odontologo por Matricula");
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(matricula);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            logger.info("No existe el odontologo");
            throw new ResourceNotFoundException("no se encontro el odontologo ");
        }
    }

}
