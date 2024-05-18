package com.example.cleanway.domain.dto.crew;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class CrewRequestDto {
    private CleanCrewDto cleanCrewDto;
    private CleanCrewProjectDto cleanCrewProjectDto;
    private CleanMyCrewDto cleanMyCrewDto;
    private CleanMyProjectDto cleanMyProjectDto;
}
