package org.tracker.animalstracker.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.tracker.animalstracker.enums.PetType;

@Entity
@DiscriminatorValue("DOG")
public class Dog extends Pet {
    @Override
    public void setPetType(PetType petType) {
        super.setPetType(PetType.DOG);
    }
}
