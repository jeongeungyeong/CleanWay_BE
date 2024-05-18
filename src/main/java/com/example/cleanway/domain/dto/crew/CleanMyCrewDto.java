package com.example.cleanway.domain.dto.crew;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CleanMyCrewDto {
    private Long userNumber;
    private Long crewNumber;
    private Long crewRoleNumber;
    private String crewJoinDate;
}
