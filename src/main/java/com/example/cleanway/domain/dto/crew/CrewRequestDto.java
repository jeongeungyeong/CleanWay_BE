package com.example.cleanway.domain.dto.crew;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.ref.PhantomReference;

@Data
@NoArgsConstructor
public class CrewRequestDto {
    private CleanCrewDto cleanCrewDto;
    private CleanCrewProjectDto cleanCrewProjectDto;
}
