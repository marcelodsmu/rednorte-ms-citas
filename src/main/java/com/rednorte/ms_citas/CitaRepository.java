package com.rednorte.ms_citas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// PATRÓN REPOSITORY APLICADO EN CITAS
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}