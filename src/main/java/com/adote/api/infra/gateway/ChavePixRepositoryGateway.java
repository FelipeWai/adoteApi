package com.adote.api.infra.gateway;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.ChavePixGateway;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;
import com.adote.api.infra.mappers.ChavePixMapper;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.ChavePixEntity;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.repositories.ChavePixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChavePixRepositoryGateway implements ChavePixGateway {


    private final GetOrganizacaoById getOrganizacaoById;

    private final ChavePixRepository chavePixRepository;

    private final ChavePixMapper chavePixMapper;
    private final OrganizacaoMapper organizacaoMapper;

    @Override
    public ChavePix createChavePix(ChavePixRequestDTO chavePixRequestDTO) {

        Organizacao organizacao = getOrganizacaoById.execute(chavePixRequestDTO.organizacao_id());
        ChavePixEntity chavePixEntity = chavePixMapper.toEntity(chavePixRequestDTO);
        chavePixEntity.setOrganizacao(organizacaoMapper.toEntity(organizacao));
        chavePixRepository.save(chavePixEntity);
        return chavePixMapper.toChavePix(chavePixEntity);

    }

    @Override
    public List<ChavePix> getAllChaves() {
        List<ChavePixEntity> chavePixEntities = chavePixRepository.findAll();
        return chavePixEntities.stream()
                .map(chavePixMapper::toChavePix)
                .toList();
    }

    @Override
    public List<ChavePix> getChavePixByOrgId(Long orgId) {
        Organizacao organizacao = getOrganizacaoById.execute(orgId);
        List<ChavePixEntity> chavePixEntityList = chavePixRepository.findAllByOrganizacao_Id(orgId);
        return chavePixEntityList.stream()
                .map(chavePixMapper::toChavePix)
                .toList();
    }

    @Override
    public ChavePix updateChaveById(Long id, ChavePixUpdateDTO updateDTO) {
        ChavePixEntity entity = chavePixRepository.findById(id);
        if (updateDTO.tipo() != null) {
            entity.setTipo(updateDTO.tipo());
        }
        if (updateDTO.chave() != null) {
            if (chavePixRepository.existsByChaveAndIdNot(updateDTO.chave(), id)) {
                return null;
            }
            entity.setChave(updateDTO.chave());
        }

        ChavePixEntity savedEntity = chavePixRepository.save(entity);
        return chavePixMapper.toChavePix(savedEntity);
    }
}
