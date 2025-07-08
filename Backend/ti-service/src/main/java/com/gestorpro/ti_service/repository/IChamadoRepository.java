package com.gestorpro.ti_service.repository;

import com.gestorpro.ti_service.model.ChamadoSuporte;
import com.gestorpro.ti_service.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IChamadoRepository extends JpaRepository<ChamadoSuporte, Long> {
    List<ChamadoSuporte> findBySolicitanteEmail(String email);
     //Busca todos os chamados que possuem um determinado status.
    List<ChamadoSuporte> findByStatus(Status status);
}