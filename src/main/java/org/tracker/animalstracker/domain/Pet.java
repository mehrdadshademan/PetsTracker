package org.tracker.animalstracker.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Indexed;
import org.tracker.animalstracker.enums.PetType;
import org.tracker.animalstracker.enums.TrackerType;

import java.util.UUID;

@Data
@DiscriminatorColumn(name = "petType")
@Entity
@NoArgsConstructor
@Table(name = "Pet",
        indexes = @Index(name = "idx_pet_type", columnList = "petType"))
public  class Pet{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "petType", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Enumerated(EnumType.STRING)
    private TrackerType trackerType;

    private Integer ownerId;

    private boolean inZone;
}
