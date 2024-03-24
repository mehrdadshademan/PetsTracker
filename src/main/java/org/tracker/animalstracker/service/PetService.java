package org.tracker.animalstracker.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.tracker.animalstracker.domain.Pet;
import org.tracker.animalstracker.dto.PetCountResult;
import org.tracker.animalstracker.dto.PetDto;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;
import org.tracker.animalstracker.exception.PetInputNotValidException;
import org.tracker.animalstracker.exception.PetNotFindException;
import org.tracker.animalstracker.mapper.PetMapper;
import org.tracker.animalstracker.repository.PetRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper mapper;
    private final Validator validator;

    /**
     * insert pet info into db
     *
     * @param petDto pet dto to provide data to insert into DB
     * @return the pets which inserted
     */
    public Optional<PetDto> createPets(PetDto petDto) {
        log.debug("call createPets... , time:{} ,petDto: {}", LocalTime.now(), petDto);
        validationPets(petDto);
        Pet pet = mappedPet(petDto);
        Pet savedPet = petRepository.save(pet);
        log.debug("pet saved: {} , time:{}", savedPet, LocalTime.now());
        return Optional.of(mapper.toPetDto(savedPet));
    }

    private void validationPets(PetDto petDto) {
        if (petDto == null) {
            log.error("The input method is null");
            throw new PetInputNotValidException("Input is null");
        }
        Set<ConstraintViolation<PetDto>> validate = validator.validate(petDto);
        if (!validate.isEmpty()) {
            log.error("the input method is not valid, petDto:{}", petDto);
            throw new ConstraintViolationException(validate);
        }
        if (petDto.getPetType() == PetType.CAT && petDto.getTrackerType() == TrackerType.MEDIUM) {
            log.error("TrackerType is not match with PetType, TrackerType:{} , PetType:{}"
                    , petDto.getTrackerType().name(), petDto.getPetType().getName());
            throw new PetInputNotValidException("TrackerType is not match with PetType");
        }
    }

    /**
     * search pets (Cats or Dogs)
     *
     * @param petDto search input
     * @return list of pets
     */
    public List<PetDto> searchPets(PetDto petDto) {
        log.debug("Start searchPets with petDto: {}", petDto);
        if (petDto == null) {
            log.error("the input method is null");
            throw new PetInputNotValidException("input method is not valid");
        }
        Pet pet = mappedPet(petDto);
        Example<Pet> petExample = Example.of(pet);
        List<Pet> petList = petRepository.findAll(petExample);
        return mapper.toPetDtoList(petList);
    }

    /*
        Generic method to map Dto to pet extends
     */
    private <T extends Pet> T mappedPet(PetDto petDto) {
        if (PetType.CAT == petDto.getPetType()) {
            return (T) mapper.toCatEntity(petDto);
        } else if (PetType.DOG == petDto.getPetType()) {
            return (T) mapper.toDogEntity(petDto);
        }
        return (T) mapper.toPetEntity(petDto);
    }

    /**
     * Show count of pets according to Group by type and track when pet is not in zone
     *
     * @return PetCountResult include count of pets group by track and type
     */
    public List<PetCountResult> countGroupByPetTypeAndTrack() {
        try {
            log.debug("Start countGroupByPetTypeAndTrack");
            return petRepository.countPetByInNotZoneGroupByTypeAndTrack();
        } catch (Exception e) {
            log.error("countGroupByPetTypeAndTrack error: {}", e.getMessage());
            throw new PetNotFindException("countGroupByPetTypeAndTrack error");
        }
    }
}
