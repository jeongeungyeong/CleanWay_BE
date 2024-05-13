package com.example.cleanway.domain.dto.crew;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CleanCrewProjectDto {
    private Long crewProjectNumber;
    private Long crewNumber;
    private String projectWriteTime;
    private String projectContent;
    private Long projectRecruitment;
    private String projectDate;
    private String projectTime;
    private Long projectRoleNumber;
    private Long userNumber;
    private Double projectSLng;
    private Double projectSLat;
    private Double projectVLng;
    private Double projectVLat;
    private Double projectDLng;
    private Double projectDLat;
}
