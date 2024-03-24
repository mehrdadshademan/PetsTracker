package org.tracker.animalstracker.enums;

import lombok.Getter;

@Getter
public enum CatTrackType {
    SMALL(TrackerType.SMALL),
    BIG(TrackerType.BIG);
    private final TrackerType trackerType;

    CatTrackType(TrackerType trackerType) {
        this.trackerType = trackerType;
    }
}
