package com.adote.api.core.usecases.animal.post;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreateAnimalCaseImpl implements CreateAnimalCase {

    private final AnimalGateway animalGateway;

    public CreateAnimalCaseImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public Animal execute(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos) {
        return animalGateway.createAnimal(animalRequestDTO, fotos);
    }
}
