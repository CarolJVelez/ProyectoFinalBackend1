package com.example.ProyectoIntegrador.service;



import com.example.ProyectoIntegrador.dao.TurnoDAOLISTA;
import com.example.ProyectoIntegrador.dao.iDao;
import com.example.ProyectoIntegrador.entity.Turno;

import java.util.List;

public class TurnoService {
    private iDao<Turno> turnoiDao;

    public TurnoService() {
        turnoiDao= new TurnoDAOLISTA();
    }
    public Turno guardarTurno(Turno turno){
        return turnoiDao.guardar(turno);
    }
    public Turno buscarPorID(Integer id){
        return turnoiDao.buscarPorID(id);
    }
    public List<Turno> listarTurnos(){
        return turnoiDao.buscarTodos();
    }
    public void actualizarTurno(Turno turno){
        turnoiDao.actualizar(turno);
    }

    public void eliminarTurno(Integer id){
        turnoiDao.eliminar(id);
    }
}
