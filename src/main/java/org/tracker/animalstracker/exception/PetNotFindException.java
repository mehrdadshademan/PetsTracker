package org.tracker.animalstracker.exception;

public class PetNotFindException extends RuntimeException {
    public PetNotFindException(String message) {
        super(message);
    }
}
