package com.gestorpro.ti_service.model.state;

import com.gestorpro.ti_service.model.ChamadoSuporte;

public interface PodeCancelar { void cancelar(ChamadoSuporte chamado, String motivo); }
