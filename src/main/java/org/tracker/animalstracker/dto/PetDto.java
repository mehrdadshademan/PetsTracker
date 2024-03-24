package org.tracker.animalstracker.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;
@Data
@NotNull
public class PetDto {
    @NotNull(message = "petType can not be Null")
    private PetType petType;
    @NotNull(message = "trackerType can not be Null")
    private TrackerType trackerType;
    @NotNull(message = "ownerId can not be Null")
    @Max(value = 2147483646, message = "owner Id can not be more than {value}")
    @Min(value = 1, message = "owner Id can not be less than {value}")
    private Integer ownerId;
    @NotNull(message = "inZone is mandatory")
    private boolean inZone;

    private boolean lostTracker;
}
