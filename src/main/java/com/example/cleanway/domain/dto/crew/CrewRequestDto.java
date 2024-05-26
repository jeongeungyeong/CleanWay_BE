package com.example.cleanway.domain.dto.crew;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class CrewRequestDto {
    private CleanCrewDto cleanCrewDto;
    private ProjectRequestDto projectRequestDto;
    private List<String> crewTagList;
}
