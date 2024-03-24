package org.tracker.animalstracker.enums;

import lombok.Getter;

@Getter
public enum PetType {
    DOG("DOG"), CAT("CAT");
    private final String name ;

    PetType(String name) {
        this.name = name;
    }
}
