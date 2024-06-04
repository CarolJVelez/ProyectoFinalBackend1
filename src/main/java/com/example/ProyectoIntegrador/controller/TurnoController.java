package com.example.ProyectoIntegrador.controller;


import com.example.ProyectoIntegrador.entity.Turno;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    public TurnoController() {
        turnoService= new TurnoService();
    }
  @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){

      PacienteService pacienteService= new PacienteService();
      OdontologoService odontologoService= new OdontologoService();
      if(pacienteService.buscarPaciente(turno.getPaciente().getId())!=null&&odontologoService.buscarOdontologo(turno.getOdontologo().getId())!=null){
          return ResponseEntity.ok(turnoService.guardarTurno(turno));
      }else{
          //bad request or not found
          return ResponseEntity.badRequest().build();
      }
          }
    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Integer id){
        Turno turno = turnoService.buscarPorID(id);
        if(turno != null){
            return ResponseEntity.ok(turno);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno){
        Turno TurnoBuscado= turnoService.buscarPorID(turno.getId());
        if(TurnoBuscado!=null){
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok(turno);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Turno> eliminarTurno(@PathVariable Integer id){
        Turno TurnoBuscado= turnoService.buscarPorID(id);
        if(TurnoBuscado!=null){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
