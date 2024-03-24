package org.tracker.animalstracker.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;

import java.util.UUID;

@Data
@DiscriminatorColumn(name = "petType")
@Entity
@NoArgsConstructor
@Table(name = "Pet",
        indexes = @Index(name = "idx_pet_type", columnList = "petType"))
public class Pet {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "petType", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Enumerated(EnumType.STRING)
    private TrackerType trackerType;

    private Integer ownerId;

    private boolean inZone;
}
