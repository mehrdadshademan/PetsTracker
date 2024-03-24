package org.tracker.animalstracker.service;


import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tracker.animalstracker.domain.Cat;
import org.tracker.animalstracker.dto.PetCountResult;
import org.tracker.animalstracker.dto.PetDto;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;
import org.tracker.animalstracker.mapper.PetMapper;
import org.tracker.animalstracker.repository.PetRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class IntegrationPetTest {
    @InjectMocks
    private PetService petService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private Validator validator;
    @Mock
    private PetMapper mapper;

    @Test
    void should_Success_When_InsertRightDataPetDto() {
        PetDto petDto = new PetDto();
        petDto.setPetType(PetType.CAT);
        petDto.setInZone(true); //in zone
        petDto.setTrackerType(TrackerType.BIG);
        petDto.setOwnerId(1);

        Cat pet = new Cat();
        pet.setPetType(petDto.getPetType());
        pet.setInZone(petDto.isInZone());
        pet.setTrackerType(petDto.getTrackerType());
        pet.setOwnerId(petDto.getOwnerId());

        when(petRepository.save(any())).thenReturn(pet);
        when(mapper.toPetDto(any())).thenReturn(petDto);
        when(mapper.toCatEntity(petDto)).thenReturn(pet);
        when(petRepository.findAll()).thenReturn(Collections.singletonList(pet));
        when(mapper.toPetDtoList(anyList())).thenReturn(Collections.singletonList(petDto));
        when(petRepository.countPetByInNotZoneGroupByTypeAndTrack()).thenReturn(Collections.emptyList());

        PetDto pets = petService.createPets(petDto).get();
        List<PetDto> petDtos = petService.searchPets(petDto);
        List<PetCountResult> petCountResults = petService.countGroupByPetTypeAndTrack();
        Assertions.assertEquals(petDtos.get(0).getOwnerId(), pets.getOwnerId());
        Assertions.assertEquals(petDtos.get(0).getTrackerType(), pets.getTrackerType());
        Assertions.assertEquals(petDtos.get(0).isInZone(), pets.isInZone());
        Assertions.assertNotEquals(petCountResults.size(), 1);
    }
}
