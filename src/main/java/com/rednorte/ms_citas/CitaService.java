package com.rednorte.ms_citas;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    public Optional<Cita> update(Long id, Cita cita) {
        return citaRepository.findById(id)
                .map(existing -> {
                    existing.setFecha(cita.getFecha());
                    existing.setEspecialidad(cita.getEspecialidad());
                    existing.setIdPaciente(cita.getIdPaciente());
                    return citaRepository.save(existing);
                });
    }

    public boolean delete(Long id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
