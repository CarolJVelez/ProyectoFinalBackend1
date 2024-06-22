package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.dto.TurnoDTO;
import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.entity.Turno;
import com.example.ProyectoIntegrador.repository.OdontologoRepository;
import com.example.ProyectoIntegrador.repository.PacienteRepository;
import com.example.ProyectoIntegrador.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public TurnoDTO guardarTurno(Turno turno){
        return turnoAturnoDTO(turnoRepository.save(turno));
    }

    public void actualizarTurno(Turno turno){
        turnoAturnoDTO(turnoRepository.save(turno));
    }

    public Optional<TurnoDTO> buscarPorID(Long id){
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        return turnoBuscado.map(this::turnoAturnoDTO);
    }

    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }

    public List<TurnoDTO> buscarTodos(){
        List<Turno> listaTurnos = turnoRepository.findAll();
        List<TurnoDTO> listaDTO = new ArrayList<>();
        for (Turno turno : listaTurnos) {
            listaDTO.add(turnoAturnoDTO(turno));
        }
        return listaDTO;
    }

    private TurnoDTO turnoAturnoDTO(Turno turno){
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setFecha(turno.getFecha());
        turnoDTO.setPacienteId(turno.getPaciente().getId());
        turnoDTO.setOdontologoId(turno.getOdontologo().getId());
        turnoDTO.setPaciente(turno.getPaciente());
        turnoDTO.setOdontologo(turno.getOdontologo());
        return turnoDTO;
    }

    public Turno turnodtoAturno(TurnoDTO turnoDTO){
        Turno turno = new Turno();
        Odontologo odontologo = odontologoRepository.findById(turnoDTO.getOdontologoId()).get();
        Paciente paciente = pacienteRepository.findById(turnoDTO.getPacienteId()).get();
        turno.setId(turnoDTO.getId());
        turno.setFecha(turnoDTO.getFecha());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        return turno;
    }
}
