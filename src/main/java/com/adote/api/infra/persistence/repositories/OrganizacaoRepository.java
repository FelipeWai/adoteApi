package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface OrganizacaoRepository extends JpaRepository<OrganizacaoEntity, Long>, JpaSpecificationExecutor<OrganizacaoEntity> {
    Optional<UserDetails> getOrganizacaoEntityByEmail(String email);
    Page<OrganizacaoEntity> findAll(Pageable pageable);

}
