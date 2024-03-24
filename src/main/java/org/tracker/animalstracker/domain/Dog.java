package org.tracker.animalstracker.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import org.tracker.animalstracker.enums.PetType;

@Entity
@Data
@DiscriminatorValue("DOG")
public class Dog extends Pet {
    @Override
    public void setPetType(PetType petType) {
        super.setPetType(PetType.DOG);
    }
}
