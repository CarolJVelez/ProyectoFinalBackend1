package com.example.ProyectoIntegrador.dao;


import com.example.ProyectoIntegrador.entity.Turno;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TurnoDAOLISTA implements iDao<Turno>{
    private static final Logger logger=Logger.getLogger(TurnoDAOLISTA.class);
    private List<Turno> turnos= new ArrayList<>();

    private Integer contadorIdTurno = 1;
    @Override
    public Turno guardar(Turno turno) {
        logger.info("iniciando las operaciones de guardado de un turno");
        //que hacemos para guardar un turno?
        System.out.println("Copntador inicial" + contadorIdTurno);
        turno.setId(contadorIdTurno++);
        PacienteDAOH2 daoPaciente= new PacienteDAOH2();
        OdontologoDAOH2 daoOdontologo= new OdontologoDAOH2();
        turno.setPaciente(daoPaciente.buscarPorID(turno.getPaciente().getId()));
        turno.setOdontologo(daoOdontologo.buscarPorID(turno.getOdontologo().getId()));
        System.out.println("Copntador final" + contadorIdTurno);
        turnos.add(turno);
        logger.info("turno guardado");
        return turno;
    }

    @Override
    public Turno buscarPorID(Integer id) {
        for (Turno turno : turnos) {
            if(turno.getId().equals(id)){
                return turno;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Turno turno) {
        eliminar(turno.getId());
        guardar(turno);
    }

    @Override
    public void eliminar(Integer id) {
        for(int i = 0; i<turnos.size(); i++){
            if(turnos.get(i).getId().equals(id)){
                turnos.remove(i);
                logger.info("Turno Eliminado");
            }else{
                logger.info("Turno no encontrado");
            }
        }
    }

    @Override
    public List<Turno> buscarTodos() {
        return turnos;
    }

    @Override
    public Turno buscarPorString(String valor) {
        return null;
    }
}
