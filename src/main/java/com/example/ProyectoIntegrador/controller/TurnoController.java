package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.dto.TurnoDTO;
import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.entity.Turno;
import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;


    public TurnoController() {
        turnoService= new TurnoService();
    }

    private static final Logger logger= Logger.getLogger(TurnoController.class);

    @PostMapping
    public ResponseEntity<TurnoDTO> guardarTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(turno.getOdontologo().getId());
        logger.info("iniciando la operacion de : Guardado de un Turno");
        if(odontologoBuscado.isPresent() && pacienteBuscado.isPresent()){
            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }else{
            logger.info("El paciente u Odontologo no existe, verifica la informacion");
            throw new BadRequestException("El paciente u Odontologo no existe, verifica la informacion");
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDTO> buscarPacienteID(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<TurnoDTO> turno = turnoService.buscarPorID(id);
        logger.info("iniciando la operacion de : Buscar un Turno");
        if (turno.isPresent()) {
            return ResponseEntity.ok(turno.get());
        } else {
            logger.info("No existe el Turno");
            throw new ResourceNotFoundException("no se encontro el turno con id: "+ id);
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTodos(){
        logger.info("iniciando la operacion de : Listar todos los Turnos");
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws ResourceNotFoundException{
        //necesitamos primeramente validar si existe o  no
        logger.info("iniciando la operacion de : Actualizar de un Turno");
        Optional<TurnoDTO> turnoBuscado= turnoService.buscarPorID(turno.getId());
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(turno.getId());
            turnoService.actualizarTurno(turno);
            logger.info("Actualizado el Turno");
            return ResponseEntity.ok("Turno actualizado con exito");
        }else{
            logger.info("no existe el Turno");
            throw new ResourceNotFoundException("No se encontro el turno para actualizar");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException{
        logger.info("iniciando la operacion de : Eliminar un Turno");
        Optional<TurnoDTO> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            logger.info("Turno eliminado con exito");
            return ResponseEntity.ok("Turno eliminado con exito");
        }else{
            logger.info("El Turno no existe");
            throw new ResourceNotFoundException("No existe el turno con el id: " + id);
        }
    }
}
