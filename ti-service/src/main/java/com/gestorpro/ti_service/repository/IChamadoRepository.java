package com.gestorpro.ti_service.repository;

import com.gestorpro.ti_service.model.ChamadoSuporte;
import com.gestorpro.ti_service.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IChamadoRepository extends JpaRepository<ChamadoSuporte, Long> {
    List<ChamadoSuporte> findBySolicitanteEmail(String email);
    /**
     * NOVO: Busca todos os chamados que possuem um determinado status.
     * Query gerada automaticamente: SELECT * FROM chamados_suporte WHERE status = ?
     * @param status O enum do status a ser pesquisado (ex: Status.ABERTO).
     * @return Uma lista de chamados que correspondem ao status.
     */
    List<ChamadoSuporte> findByStatus(Status status);
}