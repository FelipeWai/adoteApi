package com.adote.api.core.gateway;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;

import java.util.List;

public interface ChavePixGateway {

    ChavePix createChavePix(ChavePixRequestDTO chavePixRequestDTO);

    List<ChavePix> getAllChaves();

    List<ChavePix> getChavePixByOrgId(Long orgId);

    ChavePix updateChaveById(Long id, ChavePixUpdateDTO updateDTO);

}
