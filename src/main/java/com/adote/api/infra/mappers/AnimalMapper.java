package com.adote.api.infra.mappers;


import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = FotoAnimalMapper.class)
public interface AnimalMapper {

    @Mapping(target = "descricao", source = "descricao")
    AnimalEntity toEntity(AnimalRequestDTO animalRequestDTO);

    @Mapping(target = "fotos", qualifiedByName = "mapWithoutAnimal")
    Animal toAnimal(AnimalEntity animalEntity);

    AnimalResponseDTO toResponseDTO(Animal animal);

    AnimalEntity toEntity(Animal animal);
}

