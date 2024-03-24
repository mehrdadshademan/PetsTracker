package org.tracker.animalstracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetCountResult {
    private PetType petType;
    private TrackerType trackerType;
    private Long count;
}
