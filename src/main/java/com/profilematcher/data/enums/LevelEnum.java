package com.profilematcher.data.enums;

import lombok.Getter;

@Getter
public enum LevelEnum {
    NOOB(0),
    BEGINNER(1),
    INTERMEDIATE(2),
    ADVANCED(3);

    private final Integer level;

    LevelEnum(Integer level) {
        this.level = level;
    }
}
