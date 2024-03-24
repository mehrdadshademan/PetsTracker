package org.tracker.animalstracker.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.tracker.animalstracker.enums.PetType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("CAT")
public class Cat extends Pet {
    private boolean lostTracker;
    @Override
    public void setPetType(PetType petType) {
        super.setPetType(PetType.CAT);
    }
}
