package com.adote.api.infra.presentation;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.core.usecases.animal.delete.DeleteAnimalByIdCase;
import com.adote.api.core.usecases.animal.get.GetAllAnimaisCase;
import com.adote.api.core.usecases.animal.get.GetAnimalByIdCase;
import com.adote.api.core.usecases.animal.patch.UpdateAnimalCase;
import com.adote.api.core.usecases.animal.post.CreateAnimalCase;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.mappers.AnimalMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
@Tag(name = "Animal", description = "Responsavel pelo gerenciamento de animais")
public class AnimalController {

    private final CreateAnimalCase createAnimalCase;
    private final UpdateAnimalCase updateAnimalCase;
    private final GetAllAnimaisCase getAllAnimaisCase;
    private final GetAnimalByIdCase getAnimalByIdCase;
    private final DeleteAnimalByIdCase deleteAnimalByIdCase;
    private final AnimalMapper animalMapper;

    @Operation(summary = "Busca de animais", description = "Busca por todos os animais ou por organização",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna lista de animais")
    @GetMapping("/find/all")
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(required = false) TipoAnimalEnum tipo,
            @RequestParam(required = false) IdadeEnum idade,
            @RequestParam(required = false) PorteEnum porte,
            @RequestParam(required = false) SexoEnum sexo,
            @RequestParam(required = false) Long orgId,
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 20);

        Page<Animal> animalPage = getAllAnimaisCase.execute(tipo, idade, porte, sexo, orgId, pageable);

        List<AnimalResponseDTO> animalResponseDTOList = animalPage.stream()
                .map(animalMapper::toResponseDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("animals", animalResponseDTOList);
        response.put("currentPage", animalPage.getNumber());
        response.put("totalItems", animalPage.getTotalElements());
        response.put("totalPages", animalPage.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/find")
    public ResponseEntity<AnimalResponseDTO> findAnimalById(@RequestParam Long id) {
        Optional<Animal> animal = getAnimalByIdCase.execute(id);
        if(animal.isPresent()) {
            return ResponseEntity.ok(animalMapper.toResponseDTO(animal.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnimalResponseDTO> createAnimal(
            @RequestPart("dados") AnimalRequestDTO requestDTO,
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos) {

        System.out.println("descrição recebida: " + requestDTO.descricao());
        Animal newAnimal = createAnimalCase.execute(requestDTO, fotos);

        if (newAnimal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(animalMapper.toResponseDTO(newAnimal));
    }

    @PatchMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnimalResponseDTO> updateAnimal(
            @PathVariable Long id,
            @RequestPart(value = "dados", required = false) AnimalRequestDTO requestDTO,
            @RequestPart(value = "novasFotos", required = false) List<MultipartFile> novasFotos,
            @RequestPart(value = "fotosParaRemover", required = false) List<String> fotosParaRemover) {

        Animal updatedAnimal = updateAnimalCase.execute(id, requestDTO, novasFotos, fotosParaRemover);

        if (updatedAnimal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(animalMapper.toResponseDTO(updatedAnimal));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAnimalById(@RequestParam Long animalId) {
        deleteAnimalByIdCase.execute(animalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
