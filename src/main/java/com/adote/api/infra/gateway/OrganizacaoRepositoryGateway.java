package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.OrganizacaoGateway;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.repositories.OrganizacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrganizacaoRepositoryGateway implements OrganizacaoGateway {

    private final PasswordEncoder passwordEncoder;

    private final OrganizacaoMapper organizacaoMapper;
    private final OrganizacaoRepository organizacaoRepository;

    @Override
    public Page<Organizacao> getAllorganizacoes(Pageable pageable) {
        Page<OrganizacaoEntity> organizacaoEntities = organizacaoRepository.findAll(pageable);
        return organizacaoEntities.map(organizacaoMapper::toOrganizacao);
    }

    @Override
    public Organizacao createOrganizacao(Organizacao organizacao) {
        OrganizacaoEntity organizacaoEntity = organizacaoMapper.toEntity(organizacao);
        organizacaoEntity.setSenha(passwordEncoder.encode(organizacaoEntity.getSenha()));
        return organizacaoMapper.toOrganizacao(organizacaoRepository.save(organizacaoEntity));
    }

    @Override
    public Optional<Organizacao> getOrganizacaoById(Long id) {
        Optional<OrganizacaoEntity> organizacaoEntity = organizacaoRepository.findById(id);
        if (organizacaoEntity.isPresent()) {
            return Optional.of(organizacaoMapper.toOrganizacao(organizacaoEntity.get()));
        }
        return Optional.empty();
    }

    @Override
    public void deleteOrganizacaoById(Long id) {
        organizacaoRepository.deleteById(id);
    }
}
