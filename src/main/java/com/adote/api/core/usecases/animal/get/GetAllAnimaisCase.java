package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.infra.filters.animal.AnimalFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetAllAnimaisCase {

    Page<Animal> execute(AnimalFilter filter, Pageable pageable);
}
