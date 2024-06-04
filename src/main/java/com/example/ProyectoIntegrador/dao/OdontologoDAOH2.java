package com.example.ProyectoIntegrador.dao;


import com.example.ProyectoIntegrador.entity.Odontologo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements iDao<Odontologo>{

    private static final Logger logger = Logger.getLogger(OdontologoDAOH2.class);
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGOS (MATRICULA, NOMBRE, APELLIDO) VALUES(?,?,?)";
    private static final String SQL_SELECT_ONE = "SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    private static final String SQL_UPDATE="UPDATE ODONTOLOGOS SET MATRICULA=?,NOMBRE=?, APELLIDO=? WHERE ID=? ";
    private static final String SQL_DELETE ="DELETE ODONTOLOGOS WHERE ID=? ";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        logger.info("Iniciando el guardado de odontologos");
        Connection connection = null;
        try{
            connection = BD.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1,odontologo.getMatricula());
            psInsert.setString(2,odontologo.getNombre());
            psInsert.setString(3,odontologo.getApellido());
            psInsert.execute();
            ResultSet rs = psInsert.getGeneratedKeys();

            while(rs.next()){
                odontologo.setId(rs.getInt(1));
            }
            logger.info("Odontologo guardado con exito");
        }catch (Exception e) {
            logger.warn(e.getMessage());
        }

        return odontologo;
    }

    @Override
    public Odontologo buscarPorID(Integer id) {
        logger.info("iniciando la busqueda de un odontologo por id: " + id);
        Connection connection = null;
        Odontologo odontologo = null;
        try{
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement psSelectOne = connection.prepareStatement(SQL_SELECT_ONE);
            psSelectOne.setInt(1,id);
            ResultSet rs = psSelectOne.executeQuery();
            while(rs.next()){
                odontologo = new Odontologo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }

        }catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return odontologo;
    }

    @Override
    public void actualizar(Odontologo odontologo) {
       logger.info("iniciando la actualizacion de : "+odontologo.getNombre()+" con id : "+odontologo.getId());
        Connection connection= null;

        try{
            connection= BD.getConnection();
            PreparedStatement psUpdate= connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, odontologo.getMatricula());
            psUpdate.setString(2,odontologo.getNombre());
            psUpdate.setString(3, odontologo.getApellido());
            psUpdate.setInt(4,odontologo.getId());
            psUpdate.execute();

        }catch (Exception e){
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) {
        logger.info("eliminando registro");
        Connection connection = null;
        try{
            connection = BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1,id);
            psDelete.execute();
            logger.info("Odontologo eliminado");

        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info("iniciando la busqueda de todos los Odontologos");
        Connection connection= null;

        List<Odontologo> odontologos= new ArrayList<>();
        try{
            connection=BD.getConnection();
            Statement statement= connection.createStatement();
            ResultSet rs= statement.executeQuery(SQL_SELECT_ALL);

            while (rs.next()){
                Odontologo odontologo = new Odontologo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                odontologos.add(odontologo);
            }

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return odontologos;
    }

    @Override
    public Odontologo buscarPorString(String valor) {
        return null;
    }
}
