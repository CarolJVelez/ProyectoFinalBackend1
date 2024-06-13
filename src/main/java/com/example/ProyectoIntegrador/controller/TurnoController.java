package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.entity.Turno;
import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnoService;
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

  @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno) throws BadRequestException {
      Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(turno.getPaciente().getId());
      Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(turno.getOdontologo().getId());
      if(odontologoBuscado.isPresent() && pacienteBuscado.isPresent()){
          turno.setPaciente(pacienteBuscado.get());
          turno.setOdontologo(odontologoBuscado.get());
          return ResponseEntity.ok(turnoService.guardarTurno(turno));
      }else{
          //bad request or not found
          throw new BadRequestException("El paciente u Odontologo no existe, verifica la informacion");
      }
 }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Turno> buscarPacienteID(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }else{
            throw new ResourceNotFoundException("no se encontro el turno con id: "+ id);
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws BadRequestException{
        //necesitamos primeramente validar si existe o  no
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(turno.getId());
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(turno.getId());
            turnoService.guardarTurno(turno);
            return ResponseEntity.ok("turno actualizado");
        }else{
            throw new BadRequestException("No se encontro el turno para actualizar");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("turno eliminado con exito");
        }else{
            throw new ResourceNotFoundException("No existe el turno con el id: " + id);
        }
    }
}
