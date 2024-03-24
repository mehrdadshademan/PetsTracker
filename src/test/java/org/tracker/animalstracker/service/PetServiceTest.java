package org.tracker.animalstracker.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tracker.animalstracker.domain.Pet;
import org.tracker.animalstracker.dto.PetCountResult;
import org.tracker.animalstracker.dto.PetDto;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;
import org.tracker.animalstracker.exception.PetInputNotValidException;
import org.tracker.animalstracker.mapper.PetMapper;
import org.tracker.animalstracker.repository.PetRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class PetServiceTest {
    @InjectMocks
    private PetService petService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private Validator validator;
    @Mock
    private PetMapper mapper;

    private PetDto samplePetDto() {
        PetDto petDto = new PetDto();
        petDto.setPetType(PetType.CAT);
        petDto.setOwnerId(1);
        petDto.setTrackerType(TrackerType.BIG);
        petDto.setInZone(true);
        return petDto;
    }

    private Pet samplePet(PetDto petDto) {
        Pet pet = new Pet();
        pet.setPetType(petDto.getPetType());
        pet.setInZone(petDto.isInZone());
        pet.setTrackerType(petDto.getTrackerType());
        pet.setOwnerId(petDto.getOwnerId());
        return pet;
    }

    @Test
    void should_Success_When_InsertRightDataPetDto() {
        PetDto petDto = samplePetDto();
        Pet pet = samplePet(petDto);
        when(petRepository.save(any())).thenReturn(pet);
        when(mapper.toPetDto(any())).thenReturn(petDto);
        PetDto pets = petService.createPets(petDto).get();
        Assertions.assertEquals(petDto.getOwnerId(), pets.getOwnerId());
        Assertions.assertEquals(petDto.getTrackerType(), pets.getTrackerType());
        Assertions.assertEquals(petDto.isInZone(), pets.isInZone());
        verify(petRepository, times(1)).save(any());
        verify(mapper, times(1)).toPetDto(any());
    }

    @Test
    void should_ThrowException_When_InsertWrongTrackerTyperForCat() {
        PetDto petDto = samplePetDto();
        petDto.setTrackerType(TrackerType.MEDIUM);
        Pet pet = samplePet(petDto);
        when(petRepository.save(any())).thenReturn(pet);
        when(mapper.toPetDto(any())).thenReturn(petDto);
        Assertions.assertThrows(PetInputNotValidException.class, () -> petService.createPets(petDto));
    }

    @Test
    void should_ThrowException_When_InsertWrongOwnerId() {
        PetDto petDto = samplePetDto();
        petDto.setOwnerId(-1);
        Set<ConstraintViolation<PetDto>> violations = new HashSet<>();
        ConstraintViolation violation = mock(ConstraintViolation.class);
        violations.add(violation);
        when(validator.validate(petDto)).thenReturn(violations);
        Assertions.assertThrows(ConstraintViolationException.class, () -> petService.createPets(petDto));
    }

    @Test
    void should_Success_When_QueryReturnCountBaseGroupByTracker() {
        PetCountResult petCountResult = new PetCountResult(PetType.CAT, TrackerType.BIG, 2L);
        when(petRepository.countPetByInNotZoneGroupByTypeAndTrack()).thenReturn(Collections.singletonList(petCountResult));
        List<PetCountResult> results = petService.countGroupByPetTypeAndTrack();
        Assertions.assertNotNull(results);
        Assertions.assertEquals(results.stream().findFirst().get().getCount(), petCountResult.getCount());
    }

    @Test
    void should_ThrowsException_When_InputOfSearchQueryIsNull() {
        Assertions.assertThrows(PetInputNotValidException.class, () -> petService.searchPets(null));
    }
}