package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.entity.Turno;
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
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){
      Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(turno.getPaciente().getId());
      Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(turno.getOdontologo().getId());
      if(odontologoBuscado.isPresent() && pacienteBuscado.isPresent()){
          turno.setPaciente(pacienteBuscado.get());
          turno.setOdontologo(odontologoBuscado.get());
          return ResponseEntity.ok(turnoService.guardarTurno(turno));
      }else{
          //bad request or not found
          return ResponseEntity.badRequest().build();
      }
 }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Turno> buscarPacienteID(@PathVariable Long id){
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno){
        //necesitamos primeramente validar si existe o  no
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(turno.getId());
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(turno.getId());
            turnoService.guardarTurno(turno);
            return ResponseEntity.ok("turno actualizado");
        }else{
            return  ResponseEntity.badRequest().body("no se encontro turno");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id){
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("turno eliminado con exito");
        }else{
            return ResponseEntity.badRequest().body("turno no encontrado");
        }
    }
}
