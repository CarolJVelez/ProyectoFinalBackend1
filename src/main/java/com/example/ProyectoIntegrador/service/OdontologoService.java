package com.example.ProyectoIntegrador.service;



import com.example.ProyectoIntegrador.dao.OdontologoDAOH2;
import com.example.ProyectoIntegrador.dao.iDao;
import com.example.ProyectoIntegrador.entity.Odontologo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService {

    private iDao<Odontologo> odontologoiDao;

    public OdontologoService() {
        odontologoiDao =  new OdontologoDAOH2();
    }


    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoiDao.guardar(odontologo);
    }

    public Odontologo buscarOdontologo(Integer id){
        return odontologoiDao.buscarPorID(id);
    }

    public List<Odontologo> buscarTodos(){
        return odontologoiDao.buscarTodos();
    }

    public void actualizarOdontologo(Odontologo odontologo){
        odontologoiDao.actualizar(odontologo);
    }

    public void eliminarOdontologo(Integer id){
        odontologoiDao.eliminar(id);
    }

}
