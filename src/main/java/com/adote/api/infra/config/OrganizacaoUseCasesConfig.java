package com.adote.api.infra.config;

import com.adote.api.core.usecases.organizacao.delete.DeleteOrganizacaoById;
import com.adote.api.core.usecases.organizacao.delete.DeleteOrganizacaoByIdImpl;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoByIdImpl;
import com.adote.api.core.usecases.organizacao.post.CreateOrganizacaoCase;
import com.adote.api.core.usecases.organizacao.post.CreateOrganizacaoCaseImpl;
import com.adote.api.infra.gateway.OrganizacaoRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrganizacaoUseCasesConfig {

    private final OrganizacaoRepositoryGateway organizacaoRepositoryGateway;

    @Bean
    public CreateOrganizacaoCase createOrganizacaoCase(){
        return new CreateOrganizacaoCaseImpl(organizacaoRepositoryGateway);
    }

    @Bean
    public DeleteOrganizacaoById deleteOrganizacaoById(){
        return new DeleteOrganizacaoByIdImpl(organizacaoRepositoryGateway);
    }

    @Bean
    public GetOrganizacaoById getOrganizacaoById(){
        return new GetOrganizacaoByIdImpl(organizacaoRepositoryGateway);
    }


}
