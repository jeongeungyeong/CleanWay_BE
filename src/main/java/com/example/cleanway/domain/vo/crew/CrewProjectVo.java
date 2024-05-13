package com.example.cleanway.domain.vo.crew;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CrewProjectVo {
    private Long crewNumber;
    private Long userNumber;
    private String crewName;
    private String crewWriteTime;
    private String crewContent;
    private Long crewRecruitment;
    private Long crewRoleNumber;
    private Long crewProjectNumber;
    private String projectWriteTime;
    private String projectContent;
    private Long projectRecruitment;
    private String projectDate;
    private String projectTime;
    private Long projectRoleNumber;
    private Double projectSLng;
    private Double projectSLat;
    private Double projectVLng;
    private Double projectVLat;
    private Double projectDLng;
    private Double projectDLat;
    private String projectRoleName;
    private String crewRoleName;
}
