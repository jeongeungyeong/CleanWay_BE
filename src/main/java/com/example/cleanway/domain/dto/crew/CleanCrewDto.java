package com.example.cleanway.domain.dto.crew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CleanCrewDto {
    private Long crewNumber;
    private Long userNumber;
    private String crewName;
    private String crewWriteTime;
    private String crewContent;
    private Long crewRecruitment;
    private Long crewRoleNumber;
    @Builder.Default
    private String crewTag1 = "없음";
    @Builder.Default
    private String crewTag2 = "없음";
    @Builder.Default
    private String crewTag3 = "없음";
    @Builder.Default
    private String crewTag4 = "없음";
}
