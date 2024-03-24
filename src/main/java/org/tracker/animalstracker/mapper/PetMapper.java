package org.tracker.animalstracker.mapper;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.tracker.animalstracker.domain.Cat;
import org.tracker.animalstracker.domain.Dog;
import org.tracker.animalstracker.domain.Pet;
import org.tracker.animalstracker.dto.PetDto;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PetMapper {
    List<PetDto> toPetDtoList(List<Pet> pets);

    PetDto toPetDto(Pet pet);


    Cat toCatEntity(PetDto dto);

    Dog toDogEntity(PetDto dto);

    Pet toPetEntity(PetDto dto);

    @AfterMapping
    default void afterToDto(Pet pet, @MappingTarget PetDto petDto) {
        if (pet instanceof Cat) {
            Optional.ofNullable(((Cat) pet).getLostTracker())
                    .ifPresent(petDto::setLostTracker);
        }
    }
}
