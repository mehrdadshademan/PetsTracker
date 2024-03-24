package org.tracker.animalstracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tracker.animalstracker.domain.Pet;
import org.tracker.animalstracker.dto.PetCountResult;

import java.util.List;
import java.util.UUID;


@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    @Query("select new org.tracker.animalstracker.dto.PetCountResult(  p.petType , p.trackerType, COUNT(*))  from Pet p where p.inZone=false group by p.petType,p.trackerType")
    List<PetCountResult> countPetByInNotZoneGroupByTypeAndTrack();
}