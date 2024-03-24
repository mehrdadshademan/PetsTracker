package org.tracker.animalstracker.service;


import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.animalstracker.domain.Pet;
import org.tracker.animalstracker.dto.PetCountResult;
import org.tracker.animalstracker.dto.PetDto;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;
import org.tracker.animalstracker.repository.PetRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
class PetIntegrationTest {
    @Autowired
    private PetService petService;
    @Autowired
    private PetRepository petRepository;


    private PetDto createPet() {
        PetDto petDto = new PetDto();
        petDto.setPetType(PetType.CAT);
        petDto.setInZone(true); //in zone
        petDto.setTrackerType(TrackerType.BIG);
        petDto.setOwnerId(1);
        return petDto;

    }

    @Test
    void should_Success_When_InsertRightDataPetDto() {
        PetDto petDto = createPet();
        Optional<PetDto> insertedPed = petService.createPets(petDto);
        List<Pet> petRepositoryResult = petRepository.findAll();
        Assertions.assertEquals(1, petRepositoryResult.size());
        Assertions.assertEquals(petDto.getOwnerId(), insertedPed.get().getOwnerId());
        Assertions.assertEquals(petRepositoryResult.get(0).getOwnerId(), insertedPed.get().getOwnerId());
    }

    @Test
    void should_ThrowException_When_InsertWrongDataPetDto() {
        PetDto petDto = createPet();
        petDto.setOwnerId(-1);
        int size = petRepository.findAll().size();
        Assertions.assertThrows(ConstraintViolationException.class, () -> petService.createPets(petDto));
        int sizeAfterCreateMethod = petRepository.findAll().size();
        Assertions.assertEquals(size, sizeAfterCreateMethod);
    }

    @Test
    void should_Success_When_InsertDataAndSearchSameData() {
        PetDto petDto = createPet();
        Optional<PetDto> insertedPet = petService.createPets(petDto);
        List<PetDto> petDtos = petService.searchPets(petDto);
        Assertions.assertEquals(1, petDtos.size());
        Assertions.assertEquals(insertedPet.get().getOwnerId(), petDtos.get(0).getOwnerId());
    }

    @Test
    void should_Success_When_InsertDataAndSearchSameDataAndCountPets() {
        PetDto petDto = createPet(); //zone true

        PetDto exceptDto = new PetDto();
        exceptDto.setPetType(PetType.CAT);//search based on type

        PetDto petDtoSecond = createPet();
        petDtoSecond.setOwnerId(222);
        petDtoSecond.setInZone(false);

        petService.createPets(petDto);
        petService.createPets(petDtoSecond);

        List<PetDto> petDtos = petService.searchPets(exceptDto);
        List<PetCountResult> petCountResults = petService.countGroupByPetTypeAndTrack();
        Assertions.assertEquals(1, petCountResults.get(0).getCount());
        Assertions.assertEquals(2, petDtos.size());
    }
}
